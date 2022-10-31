package se.cygni.snakebotclient.model;

import se.cygni.snakebotclient.messages.GameMessage;
import se.cygni.snakebotclient.model.enums.Direction;

import java.util.concurrent.Future;


public interface SnakeStrategy {

      Future<Direction> getNextMove(GameState state);

    void onMessage(GameMessage message);

}
