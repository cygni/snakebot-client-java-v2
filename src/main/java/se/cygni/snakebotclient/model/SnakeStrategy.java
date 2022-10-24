package se.cygni.snakebotclient.model;

import se.cygni.snakebotclient.model.enums.Direction;
import se.cygni.snakebotclient.messages.MessageType;


public interface SnakeStrategy {
    // concurrent/promise: https://guava.dev/releases/snapshot/api/docs/com/google/common/util/concurrent/SettableFuture.html
    Direction getNextMove(GameState map);

    void onMessage(MessageType message);

}
