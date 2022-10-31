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

    private final Snakebot snakebot;
    private WebSocketSession session;

    private static Logger logger = LoggerFactory.getLogger(SnakebotConnection.class.getName());

    public SnakebotConnection(Snakebot service) {
        this.snakebot = service;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws SnakebotException {
        logger.debug("Connection established successfully: {}", session);
        this.session = session;
        snakebot.connectionEstablished();
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        logger.debug("Client connection closed: {}", status);
    }
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws JsonProcessingException {
        snakebot.handleInputMessage(message.getPayload());
    }
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        logger.debug("Client transport error: {}", exception.getMessage());
    }

    public void sendMessage(String message) throws IOException {

        this.session.sendMessage(new TextMessage(message));
    }
}
