package se.cygni.snakebotclient.messages.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import se.cygni.snakebotclient.messages.GameMessage;
import se.cygni.snakebotclient.model.GameMap;
import se.cygni.snakebotclient.messages.MessageType;

import java.util.UUID;

@Data
public class GameEndedEvent extends GameMessage {
    private MessageType type = MessageType.GAME_ENDED;
    private UUID playerWinnerId;
    private String playerWinnerName;
    private UUID gameId;
    private int gameTick;
    private GameMap map;
    private UUID receivingPlayerId;
    private long timestamp;

    public static GameEndedEvent fromJson(String message) throws JsonProcessingException {
        return mapper.readValue(message, GameEndedEvent.class);
    }
}
