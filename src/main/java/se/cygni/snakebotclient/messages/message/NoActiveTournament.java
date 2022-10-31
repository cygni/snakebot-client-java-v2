package se.cygni.snakebotclient.messages.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;
import se.cygni.snakebotclient.messages.GameMessage;
import se.cygni.snakebotclient.messages.MessageType;

import java.util.UUID;

@Getter
@Setter
public class NoActiveTournament extends GameMessage {
    private MessageType type = MessageType.NO_ACTIVE_TOURNAMENT;
    private UUID receivingPlayerId;
    private long timestamp;

    public static NoActiveTournament fromJson(String message) throws JsonProcessingException {
        return mapper.readValue(message, NoActiveTournament.class);
    }
}
