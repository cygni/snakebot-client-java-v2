package se.cygni.snakebotclient.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.cygni.snakebotclient.exception.SnakebotException;
import se.cygni.snakebotclient.messages.ClientInfoMessage;
import se.cygni.snakebotclient.messages.InvalidMessage;
import se.cygni.snakebotclient.messages.RegisterMoveRequest;
import se.cygni.snakebotclient.messages.event.*;
import se.cygni.snakebotclient.messages.request.StartGameRequest;
import se.cygni.snakebotclient.messages.request.RegisterPlayerRequest;
import se.cygni.snakebotclient.messages.response.PlayerRegisteredResponse;
import se.cygni.snakebotclient.model.GameState;
import se.cygni.snakebotclient.model.GameSettings;
import se.cygni.snakebotclient.model.SnakeStrategy;
import se.cygni.snakebotclient.model.enums.Direction;
import se.cygni.snakebotclient.model.enums.GameMode;
import se.cygni.snakebotclient.messages.MessageType;

import java.io.IOException;
import java.util.UUID;

public class SnakebotClient {

    private GameSettings gameSettings;
    private final SnakeStrategy snake;
    private final SnakebotCommunicationHandler communicationHandler;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(SnakebotClient.class.getName());
    private GameMode gameMode;
    private String url;
    private UUID playerId;
    private UUID gameId;

    public SnakebotClient(SnakeStrategy snake, String uri, GameSettings gameSettings) {
        this.snake = snake;
        this.gameSettings = gameSettings;

        this.communicationHandler = new SnakebotCommunicationHandler(this, uri);
    }

    public void registerPlayer() throws SnakebotException {
        RegisterPlayerRequest request = new RegisterPlayerRequest("temporary", this.gameSettings);
        try {
            communicationHandler.sendMessage(request.toJson());
        } catch (IOException e) {
            throw new SnakebotException("Failed to register player",e);
        }
    }

    /**
     * Callback from SnakebotCommunicationHandler
     * @throws SnakebotException
     */
    public void connectionEstablished() throws SnakebotException {
        sendClientInfo();
        registerPlayer();
    }

    private void sendClientInfo() throws SnakebotException {
        try {
            communicationHandler.sendMessage(ClientInfoMessage.build().toJson());
        } catch (IOException e) {
            throw new SnakebotException("Failed to send ClientInfo", e);
        }
    }

    public void handleInputMessage(String message) throws JsonProcessingException {
        MessageType type = objectMapper.convertValue(new JSONObject(message).getString("type"), MessageType.class);

        switch (type) {
            case PLAYER_REGISTERED -> {
                PlayerRegisteredResponse playerRegistered = PlayerRegisteredResponse.fromJson(message);
                this.gameMode = playerRegistered.getGameMode();
                this.playerId = playerRegistered.getReceivingPlayerId();
                this.gameId = playerRegistered.getGameId();
            }
            case GAME_LINK -> {
                GameLinkEvent gameLinkEvent = GameLinkEvent.fromJson(message);
                logger.info("Game is ready, view on: {}", gameLinkEvent.getUrl());

                if (this.gameMode == GameMode.TRAINING) {
                    communicationHandler.sendMessage(StartGameRequest.create().toJson());
                }
            }
            case GAME_STARTING -> {
                GameStartingEvent gameStartingEvent = GameStartingEvent.fromJson(message);
                gameSettings = gameStartingEvent.getGameSettings();
                logger.info("Game is starting with {} players and settings: {}", gameStartingEvent.getNoofPlayers(), gameSettings);
            }
            case MAP_UPDATE -> {
                MapUpdateEvent mapUpdateEvent = MapUpdateEvent.fromJson(message);
                GameState gameState = new GameState(mapUpdateEvent.getMap(), mapUpdateEvent.getReceivingPlayerId(), mapUpdateEvent.getGameTick(), gameSettings);
                logger.info("Map update event: {}", mapUpdateEvent);
                Direction direction = snake.getNextMove(gameState);
                RegisterMoveRequest registerMoveRequest = new RegisterMoveRequest(direction, mapUpdateEvent, playerId);
                communicationHandler.sendMessage(registerMoveRequest.toJson());
            }
            case SNAKE_DEAD -> {
                SnakeDeadEvent snakeDeadEvent = SnakeDeadEvent.fromJson(message);
                logger.info("Snake dead event: {}", snakeDeadEvent);
            }
            case GAME_RESULT -> {
                GameResultEvent gameResultEvent = GameResultEvent.fromJson(message);
                logger.info("Game result: {}", gameResultEvent);
            }
            case GAME_ENDED -> {
                GameEndedEvent gameEndedEvent = GameEndedEvent.fromJson(message);
                logger.info("Game ended event: {}", gameEndedEvent);
            }
            case INVALID_MESSAGE -> {
                InvalidMessage invalidMessage = InvalidMessage.fromJson(message);
                logger.warn("{}: {} at {}", invalidMessage.getErrorMessage(), invalidMessage.getReceivedMessage(), invalidMessage.getTimestamp());
            }

            default -> logger.warn("Unknown Message: {}", message);
        }


    }
}
