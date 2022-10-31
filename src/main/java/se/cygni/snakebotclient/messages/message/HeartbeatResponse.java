package se.cygni.snakebotclient.messages.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;
import se.cygni.snakebotclient.messages.GameMessage;
import se.cygni.snakebotclient.messages.MessageType;

import java.util.UUID;

@Getter
@Setter
public class HeartbeatResponse extends GameMessage {
    private MessageType type = MessageType.HEARTBEAT_RESPONSE;
    private UUID receivingPlayerId;
    private long timestamp;

    public static HeartbeatResponse fromJson(String json) throws JsonProcessingException {
        return mapper.readValue(json, HeartbeatResponse.class);
    }
}
