package se.cygni.snakebotclient.snakepit;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.cygni.snakebotclient.model.GameMap;
import se.cygni.snakebotclient.utils.enums.Direction;
import se.cygni.snakebotclient.utils.enums.MessageType;

public class Slippy implements SnakeStrategy {
    private static final Logger logger = LoggerFactory.getLogger(Slippy.class);

    @Override
    public Direction getNextMove(GameMap gameMap) {
        // move in random direction
        return Direction.DOWN;

    }

    @Override
    public void onMessage(MessageType message) {
        switch (message) {
            case GAME_STARTING -> logger.info("SnakeBot is starting!");
            case SNAKE_DEAD -> logger.info("SnakeBot is dead!");
        }
    }
}
