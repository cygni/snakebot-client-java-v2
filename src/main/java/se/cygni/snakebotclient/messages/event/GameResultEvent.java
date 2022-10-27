package se.cygni.snakebotclient.messages.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import se.cygni.snakebotclient.messages.GameMessage;
import se.cygni.snakebotclient.model.PlayerRank;
import se.cygni.snakebotclient.messages.MessageType;

import java.util.List;
import java.util.UUID;

@Data
public class GameResultEvent extends GameMessage {
    private MessageType type = MessageType.GAME_RESULT;
    private UUID gameId;
    private UUID receivingPlayerId;
    private long timestamp;
    private List<PlayerRank> playerRanks;


    public static GameResultEvent fromJson(String message) throws JsonProcessingException {
        return mapper.readValue(message, GameResultEvent.class);
    }
}
