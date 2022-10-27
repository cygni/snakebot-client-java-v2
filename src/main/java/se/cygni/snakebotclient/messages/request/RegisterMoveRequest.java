package se.cygni.snakebotclient.messages.request;

import lombok.Data;
import lombok.Setter;
import lombok.ToString;
import se.cygni.snakebotclient.messages.GameMessage;
import se.cygni.snakebotclient.messages.MessageType;
import se.cygni.snakebotclient.model.enums.Direction;

import java.util.UUID;

@Data
public class RegisterMoveRequest extends GameMessage {
    private MessageType type = MessageType.REGISTER_MOVE;
    private Direction direction;
    private UUID receivingPlayerId;
    private UUID gameId;
    private int gameTick;

    public RegisterMoveRequest(Direction direction, int gameTick, UUID playerId, UUID gameId) {
        this.direction = direction;
        this.receivingPlayerId = playerId;
        this.gameId = gameId;
        this.gameTick = gameTick;
    }


}
