package se.cygni.snakebotclient.client;

import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.io.IOException;

public class SnakebotCommunicationHandler implements Runnable{
    private final SnakebotConnection snakebotConnection;
    private final String uri;

    public SnakebotCommunicationHandler(Snakebot client, String uri) {
        snakebotConnection = new SnakebotConnection(client);
        this.uri = uri;
    }

    public void sendMessage(String message) {
        try {
            snakebotConnection.sendMessage(message);
        } catch (IOException e) {
            throw new RuntimeException("Exception on: " + message, e);
        }
    }

    @Override
    public void run() {
        WebSocketConnectionManager connectionManager = new WebSocketConnectionManager(
                new StandardWebSocketClient(),
                snakebotConnection, this.uri);
        WebSocketHttpHeaders handshakeHeaders = new WebSocketHttpHeaders();
        connectionManager.setHeaders(handshakeHeaders);
        connectionManager.start();
    }
}
