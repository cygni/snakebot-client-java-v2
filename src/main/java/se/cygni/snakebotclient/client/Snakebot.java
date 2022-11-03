package se.cygni.snakebotclient.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.cygni.snakebotclient.exception.SnakebotException;
import se.cygni.snakebotclient.messages.MessageType;
import se.cygni.snakebotclient.messages.message.*;
import se.cygni.snakebotclient.messages.event.*;
import se.cygni.snakebotclient.messages.request.HeartbeatRequest;
import se.cygni.snakebotclient.messages.request.RegisterMoveRequest;
import se.cygni.snakebotclient.messages.request.RegisterPlayerRequest;
import se.cygni.snakebotclient.messages.request.StartGameRequest;
import se.cygni.snakebotclient.model.GameState;
import se.cygni.snakebotclient.model.GameSettings;
import se.cygni.snakebotclient.model.SnakeStrategy;
import se.cygni.snakebotclient.model.enums.Direction;
import se.cygni.snakebotclient.model.enums.GameMode;
import se.cygni.snakebotclient.SnakebotConfiguration;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.*;

public class Snakebot {

    private final String name;
    private final GameSettings gameSettings;
    private final SnakeStrategy snake;

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final SnakebotCommunicationHandler communicationHandler;

    private UUID playerId;
    private GameMode gameMode;
    private String gameId;

    private static final Logger logger = LoggerFactory.getLogger(Snakebot.class.getName());

    // Expected time buffer required to respond within the time limit
    private static final int EXPECTED_DELAY = 50;
    private final int timeout;

    private Snakebot(SnakebotConfiguration configuration, GameSettings gameSettings) {
        this.name = configuration.getName();
        this.snake = configuration.getSnakeStrategy();
        this.gameSettings = gameSettings;
        timeout = gameSettings.getTimeInMsPerTick() - EXPECTED_DELAY;
        showWelcome(this.name, configuration);
        this.communicationHandler = new SnakebotCommunicationHandler(this, configuration.getUri());
        communicationHandler.run();
    }

    public static void start(SnakebotConfiguration configuration, GameSettings gameSettings) {
        new Snakebot(configuration, gameSettings);
    }

    void sendClientInfo() throws SnakebotException {
        try {
            communicationHandler.sendMessage(ClientInfoMessage.build().toJson());
        } catch (IOException e) {
            throw new SnakebotException("Failed to send client info", e);
        }
    }

    protected void registerPlayer() throws SnakebotException {
        RegisterPlayerRequest request = new RegisterPlayerRequest(this.name, this.gameSettings);
        try {
            communicationHandler.sendMessage(request.toJson());
        } catch (IOException e) {
            throw new SnakebotException("Failed to register player",e);
        }
    }

    /**
     * Method handling callback from SnakebotCommunicationHandler
     */
    protected void connectionEstablished() throws SnakebotException {
        sendClientInfo();
        registerPlayer();
        sendHeartbeatRequest();
    }

    private void sendHeartbeatRequest() throws SnakebotException {
        HeartbeatRequest request = new HeartbeatRequest(this.playerId);
        try {
            communicationHandler.sendMessage(request.toJson());
        } catch (IOException e) {
            throw new SnakebotException("Failed to send HeartbeatRequest",e);
        }
    }

    /**
     *
     * @param message received from Snakebot server
     * @throws JsonProcessingException
     */
    protected void handleInputMessage(String message) throws JsonProcessingException {
        MessageType type = objectMapper.convertValue(new JSONObject(message).getString("type"), MessageType.class);
        switch (type) {
            case PLAYER_REGISTERED -> handlePlayerRegistered(PlayerRegisteredResponse.fromJson(message));
            case GAME_LINK -> handleGameLink(GameLinkEvent.fromJson(message));
            case MAP_UPDATE -> handleMapUpdate(MapUpdateEvent.fromJson(message));
            case SNAKE_DEAD -> handleSnakeDead(SnakeDeadEvent.fromJson(message));
            case GAME_STARTING -> handleGameStarting(GameStartingEvent.fromJson(message));
            case GAME_RESULT -> handleGameResult(GameResultEvent.fromJson(message));
            case GAME_ENDED -> handleGameEnded(GameEndedEvent.fromJson(message));
            case INVALID_MESSAGE -> handleInvalidMessage(InvalidMessage.fromJson(message));
            case INVALID_ARENA_NAME -> handleInvalidArenaName(InvalidArenaName.fromJson(message));
            case INVALID_PLAYER_NAME -> handleInvalidPlayerName(InvalidPlayerName.fromJson(message));
            case HEARTBEAT_RESPONSE -> handleHeartbeatResponse(HeartbeatResponse.fromJson(message));
            default -> throw new RuntimeException("Unknown Message:" + message);
        }
    }

    /*
    Handle server messages
    */

    private void handleInvalidPlayerName(InvalidPlayerName invalidPlayer) {
        throw new IllegalArgumentException("The player name " + this.name +  " was invalid, reason: " + invalidPlayer.getReasonCode());
    }

    private void handleInvalidArenaName(InvalidArenaName invalidArena) {
        throw new IllegalArgumentException("Invalid arena, Reason: " + invalidArena.getReasonCode());
    }

    private void handleGameStarting(GameStartingEvent gameStartingEvent) {
        logger.info("Game is starting with {} players and settings: {}", gameStartingEvent.getNoofPlayers(), gameStartingEvent.getGameSettings());
        snake.onMessage(gameStartingEvent);
    }

    private void handlePlayerRegistered(PlayerRegisteredResponse playerRegistered) {
        this.gameMode = playerRegistered.getGameMode();
        this.playerId = playerRegistered.getReceivingPlayerId();
    }

    private void handleGameLink(GameLinkEvent gameLinkEvent) throws JsonProcessingException {
        this.gameId = gameLinkEvent.getGameId();
        logger.info("Game is ready, view on: {}", gameLinkEvent.getUrl());
        if (this.gameMode == GameMode.TRAINING) {
            communicationHandler.sendMessage(StartGameRequest.create().toJson());
        }
    }

    private void handleMapUpdate(MapUpdateEvent mapUpdateEvent) throws JsonProcessingException {
        Future<Direction> promise = executor.submit(() ->
                snake.getNextMove(GameState.fromMapUpdateEvent(mapUpdateEvent)));
        Direction direction = null;
        try {
            direction = promise.get(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            logger.warn("Failed to find direction, sending default", e);
            direction = Direction.DOWN;
        } catch (TimeoutException e) {
            logger.warn("Timed out, sending default");
            direction = Direction.DOWN;
        } finally {
            communicationHandler.sendMessage(new RegisterMoveRequest(direction, mapUpdateEvent.getGameTick(), playerId, gameId).toJson());
        }
    }

    private void handleSnakeDead(SnakeDeadEvent snakeDeadEvent) {
        showSnakeDeadAlert(this.playerId,snakeDeadEvent);
        snake.onMessage(snakeDeadEvent);
    }

    private void handleInvalidMessage(InvalidMessage invalidMessage) {
        logger.warn("{}: {} at {}", invalidMessage.getErrorMessage(), invalidMessage.getReceivedMessage(), invalidMessage.getTimestamp());
    }

    private void handleGameEnded(GameEndedEvent gameEnded) {
        logger.info("Game Ended! Winner is: {}", gameEnded.getPlayerWinnerName());

    }

    private void handleGameResult(GameResultEvent gameResult) {
        logger.info("""
                    Game Result:
                    {}""", gameResult.getPlayerRanks());
    }


    private void handleHeartbeatResponse(HeartbeatResponse response) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.schedule(() -> {
            try {
                sendHeartbeatRequest();
            } catch (SnakebotException e) {
                logger.warn("Failed to send heartbeat request");
            }
        }, 5000, TimeUnit.MILLISECONDS);
    }

    /*
        UI Helpers
     */

    private void showWelcome(String name, SnakebotConfiguration configuration) {
        logger.info("Welcome to snakebot {} and welcome to the snake pit", name);
        logger.info("Starting with configuration: {}", configuration);
    }

    private void showSnakeDeadAlert(UUID playerId, SnakeDeadEvent snakeDeadEvent) {
        if (snakeDeadEvent.getPlayerId().equals(playerId)) {
            logger.info("Your snake has died from {} at tick {}", snakeDeadEvent.getDeathReason(), snakeDeadEvent.getGameTick());
        } else {
            logger.info("Opponent {} has died from {} at tick {}",
                    snakeDeadEvent.getPlayerId(),
                    snakeDeadEvent.getDeathReason(),
                    snakeDeadEvent.getGameTick());
        }
    }

}

