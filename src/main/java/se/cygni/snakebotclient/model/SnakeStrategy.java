package se.cygni.snakebotclient.model;

import se.cygni.snakebotclient.messages.GameMessage;
import se.cygni.snakebotclient.model.enums.Direction;

public interface SnakeStrategy {

    Direction getNextMove(GameState state);

    void onMessage(GameMessage message);

}
