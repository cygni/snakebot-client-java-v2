package se.cygni.snakebotclient.messages.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;
import se.cygni.snakebotclient.messages.GameMessage;
import se.cygni.snakebotclient.messages.MessageType;

import java.util.UUID;

@Getter
@Setter
public class ArenaIsFull extends GameMessage {
    private MessageType type = MessageType.ARENA_IS_FULL;
    private int playersConnected;
    private UUID receivingPlayerId;
    private long timestamp;

    public static ArenaIsFull fromJson(String message) throws JsonProcessingException {
        return mapper.readValue(message, ArenaIsFull.class);
    }
}
