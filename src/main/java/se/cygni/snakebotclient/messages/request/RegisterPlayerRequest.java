package se.cygni.snakebotclient.messages.request;

import lombok.Getter;
import lombok.Setter;
import se.cygni.snakebotclient.messages.GameMessage;
import se.cygni.snakebotclient.messages.MessageType;
import se.cygni.snakebotclient.model.GameSettings;

@Getter
@Setter
public class RegisterPlayerRequest extends GameMessage {
    private MessageType type = MessageType.REGISTER_PLAYER;
    private String playerName;
    private GameSettings gameSettings;

    public RegisterPlayerRequest(String playerName, GameSettings gameSettings) {
        this.playerName = playerName;
        this.gameSettings = gameSettings;
    }
}
