package se.cygni.snakebotclient.model;

import lombok.Getter;
import lombok.ToString;
import se.cygni.snakebotclient.model.enums.Direction;

import java.util.List;
import java.util.UUID;

public record Snake(UUID id, String name, Direction direction, List<Coordinate> snakeCoordinates, GameState gameState) {

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
