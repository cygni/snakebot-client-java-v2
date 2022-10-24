package se.cygni.snakebotclient.snakepit;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.cygni.snakebotclient.model.*;
import se.cygni.snakebotclient.model.enums.Direction;
import se.cygni.snakebotclient.messages.MessageType;

import java.util.Arrays;
import java.util.List;

public class Slippy implements SnakeStrategy {
    private static final Logger logger = LoggerFactory.getLogger(Slippy.class);

    @Override
    public Direction getNextMove(GameState map) {
        //Coordinate head = map.getPlayerSnake().getSnakeCoordinates().get(0);
        Snake snake =  map.getPlayerSnake();
        List<Direction> possibleMoves = Arrays.stream(Direction.values()).toList().stream().filter(snake::canMoveInDirection).toList();

        logger.info("Possible moves: {}", possibleMoves);
        return possibleMoves.get(0);
        /*
        Direction currentDir = snake.getDirection();
        Coordinate next = snake.getSnakeCoordinates().get(0).translateByDirection(currentDir);

        // move in random direction
        return Direction.DOWN;
*/
    }

    @Override
    public void onMessage(MessageType message) {
        switch (message) {
            case GAME_STARTING -> logger.info("SnakeBot is starting!");
            case SNAKE_DEAD -> logger.info("SnakeBot is dead!");
        }
    }
}
