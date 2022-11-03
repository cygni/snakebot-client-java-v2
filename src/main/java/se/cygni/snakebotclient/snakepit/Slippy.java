package se.cygni.snakebotclient.snakepit;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.cygni.snakebotclient.messages.GameMessage;
import se.cygni.snakebotclient.model.*;
import se.cygni.snakebotclient.model.enums.Direction;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Slippy implements SnakeStrategy {


    private static final Logger logger = LoggerFactory.getLogger(Slippy.class);
    private static final Random random = new Random();


    /**
     * Slippy is not the slipperiest snake of the pit, but you can help him make better decisions my changing the getNextMove() function.
     *
     * @param state The current state of the game, contains all the needed information to determine the next move
     * @return the direction of the snakes next move.
     */
    @Override
    public Direction getNextMove(GameState state) {
        // TODO Implement your logic here
        Snake snake =  state.getPlayerSnake();
        List<Direction> possibleMoves = Arrays.stream(Direction.values()).toList().stream().filter(snake::canMoveInDirection).toList();

        if (!possibleMoves.isEmpty()) {
            return pickRandom(possibleMoves);
        } else {
            logger.info("Slippy panics as there is no possible moves");
            return Direction.DOWN;
        }
    }

    private static List<Direction> getPossibleMoves(Snake snake) {
        return Arrays.stream(Direction.values()).toList().stream().filter(snake::canMoveInDirection).toList();
    }

    private static Direction pickRandom(List<Direction> possibleMoves) {
        int choice = random.nextInt(0, possibleMoves.size());
        return possibleMoves.get(choice);
    }

    @Override
    public void onMessage(GameMessage message) {
        // If you want to do special case handling on input from server this can be done here:
        switch (message.getType()) {
            case GAME_STARTING -> logger.info("SnakeBot is starting!");
            case SNAKE_DEAD -> logger.info("A snake has died!");
        }
    }

}
