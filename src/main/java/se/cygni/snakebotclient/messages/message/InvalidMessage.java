package se.cygni.snakebotclient.messages.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;
import se.cygni.snakebotclient.messages.GameMessage;
import se.cygni.snakebotclient.messages.MessageType;

import java.util.UUID;

@Getter
@Setter
public class InvalidMessage extends GameMessage {
    private MessageType type = MessageType.INVALID_MESSAGE;
    private String errorMessage;
    private String receivedMessage;
    private UUID receivingPlayerId;
    private long timestamp;

    public static InvalidMessage fromJson(String message) throws JsonProcessingException {
        return mapper.readValue(message, InvalidMessage.class);
    }
}
