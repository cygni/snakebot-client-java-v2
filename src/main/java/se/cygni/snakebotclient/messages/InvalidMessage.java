package se.cygni.snakebotclient.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;

import java.util.UUID;

@Data
public class InvalidMessage extends SnakebotMessage {
    private MessageType type = MessageType.INVALID_MESSAGE;
    private String errorMessage;
    private String receivedMessage;
    private UUID receivingPlayerId;
    private long timestamp;

    public static InvalidMessage fromJson(String message) throws JsonProcessingException {
        return mapper.readValue(message, InvalidMessage.class);
    }
}
