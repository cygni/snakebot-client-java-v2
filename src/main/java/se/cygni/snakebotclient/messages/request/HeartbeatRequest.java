package se.cygni.snakebotclient.messages.request;

import lombok.Getter;
import lombok.Setter;
import se.cygni.snakebotclient.messages.GameMessage;
import se.cygni.snakebotclient.messages.MessageType;

import java.util.UUID;

@Getter
@Setter
public class HeartbeatRequest extends GameMessage {
    private MessageType type = MessageType.HEARTBEAT_REQUEST;
    private UUID receivingPlayerId;


    public HeartbeatRequest(UUID receivingPlayerId) {
        this.receivingPlayerId = receivingPlayerId;
    }


}
