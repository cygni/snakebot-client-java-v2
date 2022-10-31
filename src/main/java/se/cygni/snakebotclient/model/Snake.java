package se.cygni.snakebotclient.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import se.cygni.snakebotclient.model.enums.Direction;

import java.util.*;

@Getter
@Setter
@ToString
public final class Snake {
    private final UUID id;
    private final String name;
    private final Direction direction;
    private final List<Coordinate> snakeCoordinates;
    private final GameState gameState;

    public Snake(UUID id, String name, Direction direction, List<Coordinate> snakeCoordinates, GameState gameState) {
        this.id = id;
        this.name = name;
        this.direction = direction;
        this.snakeCoordinates = snakeCoordinates;
        this.gameState = gameState;
    }

    public static Snake fromSnakeInfo(SnakeInfo snakeInfo, int width, GameState gameState) {
        /*if (snakeInfo.getName().equals("Player")) {
            System.out.println("Points: " + snakeInfo.getPoints());
        }*/
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
        if (this.snakeCoordinates.size() > 1 && head.getDirection(this.snakeCoordinates.get(1)) == direction) return false;
        return this.gameState.freeTile(next);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Snake) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.direction, that.direction) &&
                Objects.equals(this.snakeCoordinates, that.snakeCoordinates) &&
                Objects.equals(this.gameState, that.gameState);
    }

}
