package se.cygni.snakebotclient.messages.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import se.cygni.snakebotclient.messages.SnakebotMessage;
import se.cygni.snakebotclient.messages.MessageType;

import java.util.UUID;

@Data
public class GameLinkEvent extends SnakebotMessage {
    private MessageType type = MessageType.GAME_LINK;
    private UUID gameId;
    private String url;
    private UUID receivingPlayerId;
    private long timestamp;

    public static GameLinkEvent fromJson(String json) throws JsonProcessingException {
        return mapper.readValue(json, GameLinkEvent.class);
    }
}
