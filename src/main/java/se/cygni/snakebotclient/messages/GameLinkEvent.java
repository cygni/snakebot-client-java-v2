package se.cygni.snakebotclient.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import se.cygni.snakebotclient.messages.response.PlayerRegisteredResponse;
import se.cygni.snakebotclient.utils.enums.MessageType;

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
