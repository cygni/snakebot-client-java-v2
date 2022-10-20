package se.cygni.snakebotclient.client;

import se.cygni.snakebotclient.exception.SnakebotException;
import se.cygni.snakebotclient.messages.RegisterPlayerRequest;
import se.cygni.snakebotclient.model.GameSettings;
import se.cygni.snakebotclient.snakepit.SnakeStrategy;

import java.io.IOException;

public class SnakebotClient {

    private final GameSettings gameSettings;
    private final SnakeStrategy snake;
    private final SnakebotCommunicationHandler communicationHandler;

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

    public void handleInputMessage(String message) {


    }
}
