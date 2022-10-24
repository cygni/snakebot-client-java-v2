package se.cygni.snakebotclient.model;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.cygni.snakebotclient.model.enums.Direction;

import java.util.List;
import java.util.UUID;

@Data
public class Snake {
    private final UUID id;
    private final String name;
    private final Direction direction;
    private final List<Coordinate> snakeCoordinates;
    private final GameState gameState;

    private static Logger logger = LoggerFactory.getLogger(Snake.class);

    public Snake(UUID id, String name, Direction direction, List<Coordinate> snakeCoordinates, GameState gameState) {
        this.id = id;
        this.name = name;
        this.direction = direction;
        this.snakeCoordinates = snakeCoordinates;
        this.gameState = gameState;
    }

    public static Snake fromSnakeInfo(SnakeInfo snakeInfo, int width, GameState gameState) {
        List<Coordinate> snakeCoordinates = snakeInfo.getPositions().stream().map(c -> Coordinate.fromPosition(c, width)).toList();

        Direction direction = null;
        if (snakeCoordinates.size() > 1) {
            direction = snakeCoordinates.get(0).getDirection(snakeCoordinates.get(1));
        }

        return new Snake(snakeInfo.getId(), snakeInfo.getName(), direction, snakeCoordinates, gameState);

    }

    public boolean canMoveInDirection(Direction direction) {
        Coordinate head = this.snakeCoordinates.get(0);
        Coordinate next = head.translateByDirection(direction);
        return this.gameState.freeTile(next);
    }
}
