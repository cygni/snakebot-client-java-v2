package se.cygni.snakebotclient.messages;

import lombok.Data;
import se.cygni.snakebotclient.messages.event.MapUpdateEvent;
import se.cygni.snakebotclient.model.enums.Direction;

import java.util.UUID;

@Data
public class RegisterMoveRequest extends SnakebotMessage {
    private MessageType type = MessageType.REGISTER_MOVE;
    private Direction direction;
    private UUID receivingPlayerId;
    private UUID gameId;
    private int gameTick;

    public RegisterMoveRequest(Direction direction, MapUpdateEvent mapUpdateEvent, UUID playerId) {
        this.direction = direction;
        this.receivingPlayerId = playerId;
        this.gameId = mapUpdateEvent.getGameId();
        this.gameTick = mapUpdateEvent.getGameTick();
    }

}
