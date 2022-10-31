package se.cygni.snakebotclient.messages.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;
import se.cygni.snakebotclient.messages.GameMessage;
import se.cygni.snakebotclient.model.GameMap;
import se.cygni.snakebotclient.messages.MessageType;

import java.util.UUID;
@Getter
@Setter
public class MapUpdateEvent extends GameMessage {
    MessageType type = MessageType.MAP_UPDATE;
    private int gameTick;
    private UUID gameId;
    private GameMap map;
    private UUID receivingPlayerId;
    private long timestamp;
    public static MapUpdateEvent fromJson(String message) throws JsonProcessingException {
        return mapper.readValue(message, MapUpdateEvent.class);
    }
}
