package se.cygni.snakebotclient.snakepit;

import se.cygni.snakebotclient.model.GameMap;
import se.cygni.snakebotclient.utils.enums.Direction;
import se.cygni.snakebotclient.utils.enums.MessageType;


public interface SnakeStrategy {
    // concurrent/promise: https://guava.dev/releases/snapshot/api/docs/com/google/common/util/concurrent/SettableFuture.html
    Direction getNextMove(GameMap gameMap);

    void onMessage(MessageType message);

}
