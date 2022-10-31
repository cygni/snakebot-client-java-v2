package se.cygni.snakebotclient.messages.request;

import lombok.Getter;
import lombok.Setter;
import se.cygni.snakebotclient.messages.GameMessage;
import se.cygni.snakebotclient.messages.MessageType;

@Getter
@Setter
public class StartGameRequest extends GameMessage {
    MessageType type = MessageType.START_GAME;

    public static StartGameRequest create() {
        return new StartGameRequest();
    }


}
