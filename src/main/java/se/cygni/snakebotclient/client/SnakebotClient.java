package se.cygni.snakebotclient.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.cygni.snakebotclient.exception.SnakebotException;
import se.cygni.snakebotclient.messages.ClientInfoMessage;
import se.cygni.snakebotclient.messages.GameLinkEvent;
import se.cygni.snakebotclient.messages.request.RegisterPlayerRequest;
import se.cygni.snakebotclient.messages.response.PlayerRegisteredResponse;
import se.cygni.snakebotclient.model.GameSettings;
import se.cygni.snakebotclient.snakepit.SnakeStrategy;
import se.cygni.snakebotclient.utils.enums.MessageType;

import java.io.IOException;

public class SnakebotClient {

    private final GameSettings gameSettings;
    private final SnakeStrategy snake;
    private final SnakebotCommunicationHandler communicationHandler;
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(SnakebotClient.class.getName());

    public SnakebotClient(SnakeStrategy snake, String uri, GameSettings gameSettings) {
        this.snake = snake;
        this.gameSettings = gameSettings;

        this.communicationHandler = new SnakebotCommunicationHandler(this, uri);
    }

    public void registerPlayer() throws SnakebotException {
        RegisterPlayerRequest request = new RegisterPlayerRequest("temporary", this.gameSettings);
        try {
            communicationHandler.sendMessage(request.toJsonMessage());
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
            communicationHandler.sendMessage(ClientInfoMessage.build().toJsonMessage());
        } catch (IOException e) {
            throw new SnakebotException("Failed to send ClientInfo", e);
        }
    }

    public void handleInputMessage(String message) throws JsonProcessingException {
        MessageType type = objectMapper.convertValue(new JSONObject(message).getString("type"), MessageType.class);
        //SnakebotMessage sbMessage = new ObjectMapper().convertValue(message, SnakebotMessage.class);
        switch (type) {
            case GAME_LINK:
                GameLinkEvent gameLinkEvent = GameLinkEvent.fromJson(message);
                logger.info(gameLinkEvent.toString());
                break;
            case PLAYER_REGISTERED:
                PlayerRegisteredResponse playerRegistered = PlayerRegisteredResponse.fromJson(message);
                logger.info(playerRegistered.toString());
                break;
            default:
                logger.warn("Unknown Message" + message );

        }


    }
}
