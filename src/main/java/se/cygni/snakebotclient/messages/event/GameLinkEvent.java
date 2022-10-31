package se.cygni.snakebotclient.messages.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;
import se.cygni.snakebotclient.messages.GameMessage;
import se.cygni.snakebotclient.messages.MessageType;

import java.util.UUID;

@Getter
@Setter
public class GameLinkEvent extends GameMessage {
    private MessageType type = MessageType.GAME_LINK;
    private String gameId;
    private String url;
    private UUID receivingPlayerId;
    private long timestamp;

    public static GameLinkEvent fromJson(String json) throws JsonProcessingException {
        return mapper.readValue(json, GameLinkEvent.class);
    }
}
