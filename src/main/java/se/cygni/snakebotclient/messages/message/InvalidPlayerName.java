package se.cygni.snakebotclient.messages.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;
import se.cygni.snakebotclient.messages.GameMessage;
import se.cygni.snakebotclient.messages.MessageType;

import java.util.UUID;

@Getter
@Setter
public class InvalidPlayerName extends GameMessage {
    private MessageType type = MessageType.INVALID_PLAYER_NAME;
    private String reasonCode;
    private UUID receivingPlayerId;
    private long timestamp;

    public static InvalidPlayerName fromJson(String message) throws JsonProcessingException {
        return mapper.readValue(message, InvalidPlayerName.class);
    }
}
