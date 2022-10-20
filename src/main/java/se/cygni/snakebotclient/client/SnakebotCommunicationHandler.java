package se.cygni.snakebotclient.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.io.IOException;

public class SnakebotCommunicationHandler {

    private final SnakebotConnection snakebotConnection;


    private static final Logger logger = LoggerFactory.getLogger(SnakebotCommunicationHandler.class.getName());

    public SnakebotCommunicationHandler(SnakebotClient service, String uri) {
        snakebotConnection = new SnakebotConnection(service);

        WebSocketConnectionManager connectionManager = new WebSocketConnectionManager(
                new StandardWebSocketClient(),
                snakebotConnection, uri);
        WebSocketHttpHeaders handshakeHeaders = new WebSocketHttpHeaders();
        connectionManager.setHeaders(handshakeHeaders);
        connectionManager.start();
    }

    public void sendMessage(String message) {
        try {
            snakebotConnection.sendMessage(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
