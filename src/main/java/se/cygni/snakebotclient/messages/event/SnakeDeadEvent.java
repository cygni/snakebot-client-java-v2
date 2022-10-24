package se.cygni.snakebotclient.messages.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import se.cygni.snakebotclient.messages.SnakebotMessage;
import se.cygni.snakebotclient.messages.MessageType;

import java.util.UUID;

@Data
public class SnakeDeadEvent extends SnakebotMessage {
    private MessageType type = MessageType.SNAKE_DEAD;
    private String deathReason;
    private String playerId;
    private int x;
    private int y;
    private UUID gameId;
    private int gameTick;
    private UUID receivingPlayerId;
    private long timestamp;

    public static SnakeDeadEvent fromJson(String message) throws JsonProcessingException {
        return mapper.readValue(message, SnakeDeadEvent.class);
    }
}