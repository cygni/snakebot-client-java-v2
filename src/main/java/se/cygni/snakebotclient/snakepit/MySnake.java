package se.cygni.snakebotclient.snakepit;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.cygni.snakebotclient.exception.SnakebotException;
import se.cygni.snakebotclient.messages.GameMessage;
import se.cygni.snakebotclient.model.GameState;
import se.cygni.snakebotclient.model.SnakeStrategy;
import se.cygni.snakebotclient.model.enums.Direction;

/**
 * A completely fresh snake implementation
 */
public class MySnake implements SnakeStrategy {

    private static final Logger logger = LoggerFactory.getLogger(MySnake.class);
    @SneakyThrows
    @Override
    public Direction getNextMove(GameState state) {
        // TODO Implement your logic here
        throw new SnakebotException("Implement your solution here");
    }

    @Override
    public void onMessage(GameMessage message) {
        // TODO If you want to do special case handling on input from server this can be done here:
        switch (message.getType()) {
            case GAME_STARTING -> logger.info("MySnake is starting!");
            case SNAKE_DEAD -> logger.info("MySnake has died!");
        }
    }

}
