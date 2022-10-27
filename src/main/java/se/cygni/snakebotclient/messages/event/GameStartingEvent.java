package se.cygni.snakebotclient.messages.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import se.cygni.snakebotclient.messages.GameMessage;
import se.cygni.snakebotclient.model.GameMap;
import se.cygni.snakebotclient.model.GameSettings;
import se.cygni.snakebotclient.messages.MessageType;

import java.util.UUID;

@Data
public class GameStartingEvent extends GameMessage {
    private MessageType type = MessageType.GAME_STARTING;
    private UUID gameId;
    private GameMap map;
    private long timestamp;
    private int noofPlayers;
    private int width;
    private int height;
    private GameSettings gameSettings;
    private UUID receivingPlayerId;
    public static GameStartingEvent fromJson(String message) throws JsonProcessingException {
        return mapper.readValue(message, GameStartingEvent.class);
    }

}
