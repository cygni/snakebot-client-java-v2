package se.cygni.snakebotclient.messages.request;

import lombok.Data;
import se.cygni.snakebotclient.messages.SnakebotMessage;
import se.cygni.snakebotclient.model.GameSettings;
import se.cygni.snakebotclient.utils.enums.MessageType;

@Data
public class RegisterPlayerRequest extends SnakebotMessage {
    private MessageType type = MessageType.REGISTER_PLAYER;
    private String playerName;
    private GameSettings gameSettings;

    public RegisterPlayerRequest(String playerName, GameSettings gameSettings) {
        this.playerName = playerName;
        this.gameSettings = gameSettings;
    }
}
