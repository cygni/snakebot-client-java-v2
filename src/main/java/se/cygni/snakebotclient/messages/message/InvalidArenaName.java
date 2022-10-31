package se.cygni.snakebotclient.messages.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;
import se.cygni.snakebotclient.messages.GameMessage;
import se.cygni.snakebotclient.messages.MessageType;

import java.util.UUID;

@Getter
@Setter
public class InvalidArenaName extends GameMessage {
    private MessageType type = MessageType.INVALID_ARENA_NAME;
    private String reasonCode;
    private UUID receivingPlayerId;
    private long timestamp;

    public static InvalidArenaName fromJson(String message) throws JsonProcessingException {
        return mapper.readValue(message, InvalidArenaName.class);
    }
}
