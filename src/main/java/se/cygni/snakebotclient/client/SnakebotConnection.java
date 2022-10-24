package se.cygni.snakebotclient.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import se.cygni.snakebotclient.exception.SnakebotException;

import java.io.IOException;

public class SnakebotConnection extends TextWebSocketHandler {

    private final SnakebotClient snakebotClient;
    private WebSocketSession session;

    private static Logger logger = LoggerFactory.getLogger(SnakebotConnection.class.getName());

    public SnakebotConnection(SnakebotClient service) {
        this.snakebotClient = service;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws SnakebotException {
        logger.info("Connection established successfully: {}", session);
        this.session = session;
        snakebotClient.connectionEstablished();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        logger.info("Client connection closed: {}", status);
    }
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            snakebotClient.handleInputMessage(message.getPayload());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        logger.info("Client transport error: {}", exception.getMessage());
    }

    public void sendMessage(String message) throws IOException {
        logger.info("Sending message: {}", message);
        this.session.sendMessage(new TextMessage(message));
    }
}
