package se.cygni.snakebotclient.messages;

import lombok.Data;
import se.cygni.snakebotclient.model.GameSettings;

@Data
public class RegisterPlayerRequest extends SnakebotMessage{
    private String type = "se.cygni.snake.api.request.RegisterPlayer";
    private String playerName;
    private GameSettings gameSettings;

    public RegisterPlayerRequest(String playerName, GameSettings gameSettings) {
        this.playerName = playerName;
        this.gameSettings = gameSettings;
    }
}
