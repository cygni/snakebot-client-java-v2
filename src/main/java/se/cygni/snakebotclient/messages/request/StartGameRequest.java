package se.cygni.snakebotclient.messages.request;

import lombok.Data;
import se.cygni.snakebotclient.messages.SnakebotMessage;
import se.cygni.snakebotclient.messages.MessageType;

@Data
public class StartGameRequest extends SnakebotMessage {
    MessageType type = MessageType.START_GAME;

    public static StartGameRequest create() {
        return new StartGameRequest();
    }


}
