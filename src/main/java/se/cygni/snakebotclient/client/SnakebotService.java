package se.cygni.snakebotclient.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import se.cygni.snakebotclient.exception.SnakebotException;
import se.cygni.snakebotclient.messages.MessageType;
import se.cygni.snakebotclient.messages.message.ClientInfoMessage;
import se.cygni.snakebotclient.messages.event.*;
import se.cygni.snakebotclient.messages.message.InvalidMessage;
import se.cygni.snakebotclient.messages.message.PlayerRegisteredResponse;
import se.cygni.snakebotclient.messages.request.RegisterMoveRequest;
import se.cygni.snakebotclient.messages.request.RegisterPlayerRequest;
import se.cygni.snakebotclient.messages.request.StartGameRequest;
import se.cygni.snakebotclient.model.GameState;
import se.cygni.snakebotclient.model.GameSettings;
import se.cygni.snakebotclient.model.SnakeStrategy;
import se.cygni.snakebotclient.model.enums.Direction;
import se.cygni.snakebotclient.model.enums.GameMode;
import se.cygni.snakebotclient.view.SnakebotTerminalUI;
import se.cygni.snakebotclient.view.SnakebotUI;

import java.io.IOException;
import java.util.UUID;

public class SnakebotService {

    private GameSettings gameSettings;
    private final SnakeStrategy snake;

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final SnakebotCommunicationHandler communicationHandler;

    private UUID playerId;
    private GameMode gameMode;
    private UUID gameId;
    private final SnakebotUI snakebotUI;

    public SnakebotService(SnakeStrategy snake, String uri, GameSettings gameSettings) {
        this.snake = snake;
        this.gameSettings = gameSettings;
        this.snakebotUI = new SnakebotTerminalUI();
        this.communicationHandler = new SnakebotCommunicationHandler(this, uri);
    }

    public void registerPlayer() throws SnakebotException {
        RegisterPlayerRequest request = new RegisterPlayerRequest(snake.getName(), this.gameSettings);
        try {
            communicationHandler.sendMessage(request.toJson());
        } catch (IOException e) {
            throw new SnakebotException("Failed to register player",e);
        }
    }

    public void sendClientInfo() throws SnakebotException {
        try {
            communicationHandler.sendMessage(ClientInfoMessage.build().toJson());
        } catch (IOException e) {
            throw new SnakebotException("Failed to send client info", e);
        }
    }


    /**
     * Method handling callback from SnakebotCommunicationHandler
     * @throws SnakebotException if connection cannot be established
     */
    public void connectionEstablished() throws SnakebotException {
        sendClientInfo();
        registerPlayer();
    }

    /**
     *
     * @param message received from Snakebot server
     * @throws JsonProcessingException
     */
    public void handleInputMessage(String message) throws JsonProcessingException {
        MessageType type = objectMapper.convertValue(new JSONObject(message).getString("type"), MessageType.class);

        switch (type) {
            case PLAYER_REGISTERED -> handlePlayerRegistered(PlayerRegisteredResponse.fromJson(message));
            case GAME_LINK -> handleGameLink(GameLinkEvent.fromJson(message));
            case MAP_UPDATE -> handleMapUpdate(MapUpdateEvent.fromJson(message));
            case SNAKE_DEAD -> handleSnakeDead(SnakeDeadEvent.fromJson(message));
            case GAME_STARTING -> handleGameStarting(GameStartingEvent.fromJson(message));
            case GAME_RESULT -> handleGameResult(message);
            case GAME_ENDED -> handleGameEnded(message);
            case INVALID_MESSAGE -> handleInvalidMessage(message);
            default -> throw new RuntimeException("Unknown Message:" + message);
        }
    }

    /*
    Handle server messages
    */

    private void handleGameStarting(GameStartingEvent gameStartingEvent) {
        snakebotUI.gameHasStarted(gameStartingEvent);
        snake.onMessage(gameStartingEvent);
    }

    private void handlePlayerRegistered(PlayerRegisteredResponse playerRegistered) {
        this.gameMode = playerRegistered.getGameMode();
        this.gameId = playerRegistered.getGameId();
        this.playerId = playerRegistered.getReceivingPlayerId();
    }

    private void handleGameLink(GameLinkEvent gameLinkEvent) throws JsonProcessingException {
        snakebotUI.sendGameInfo(gameLinkEvent);
        if (this.gameMode == GameMode.TRAINING) {
            communicationHandler.sendMessage(StartGameRequest.create().toJson());
        }
    }

    private void handleMapUpdate(MapUpdateEvent mapUpdateEvent) throws JsonProcessingException {
        Direction direction = snake.getNextMove(GameState.fromMapUpdateEvent(mapUpdateEvent));
        communicationHandler.sendMessage(new RegisterMoveRequest(direction, mapUpdateEvent.getGameTick(), playerId, gameId).toJson());
    }

    private void handleSnakeDead(SnakeDeadEvent snakeDeadEvent) {
        snakebotUI.sendSnakeDeadAlert(this.playerId,snakeDeadEvent);
        snake.onMessage(snakeDeadEvent);
    }

    private void handleInvalidMessage(String message) throws JsonProcessingException {
        InvalidMessage invalidMessage = InvalidMessage.fromJson(message);
        snakebotUI.invalidMessageReceived(invalidMessage);

    }

    private void handleGameEnded(String message) throws JsonProcessingException {
        GameEndedEvent gameEnded = GameEndedEvent.fromJson(message);
        snakebotUI.gameHasEnded(gameEnded);

    }

    private void handleGameResult(String message) throws JsonProcessingException {
        GameResultEvent gameResult = GameResultEvent.fromJson(message);
        snakebotUI.showGameResults(gameResult);
    }

}

