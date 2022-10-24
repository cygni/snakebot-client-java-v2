package se.cygni.snakebotclient.model;

import se.cygni.snakebotclient.model.enums.Direction;

import java.awt.*;

public class Coordinate extends Point {
    public Coordinate(int x, int y) {
        super(x, y);
    }

    /**
     * Converts a position in the flattened single array representation
     * of the Map to a 2D Coordinate.
     * @param position The position in the flattened array.
     * @param mapWidth The width of the map.
     * @return A new Coordinate of the position.
     */
    public static Coordinate fromPosition(int position, int mapWidth) {
        int x = position % mapWidth;
        return new Coordinate(x, (position - x) / mapWidth);
    }

    /**
     * Checks if this coordinate is within a square defined by northwest and southeast coordinates.
     * @param topLeftCorner Coordinate of the top left corner.
     * @param bottomRightCorner Coordinate of the bottom right corner.
     * @return True if this coordinate is within the square.
     */
    public boolean isWithinSquare(Coordinate topLeftCorner, Coordinate bottomRightCorner) {
        return x >= topLeftCorner.x && x <= bottomRightCorner.x && y >= topLeftCorner.y && y <= bottomRightCorner.y;
    }

    /**
     * Check if a coordinate is outside the game map.
     *
     * @param mapHeight Height of the map.
     * @param mapWidth  Width of the map.
     */
    public boolean isOutOfBounds(int mapWidth, int mapHeight) {
        return x < 0 || y < 0 || x >= mapWidth || y >= mapHeight;
    }

    /**
     * Calculates the euclidian distance from this point to another point.
     * Note that eculidan distance will walk diagonally.
     * @param other Goal coordinate.
     * @return Distance in map units.
     */
    public double getEuclidianDistance(Coordinate other) {
        int xDiff = x - other.x;
        int yDiff = y - other.y;
        return Math.sqrt((xDiff) * (xDiff) + (yDiff) * (yDiff));
    }

    /**
     * Calculates the Manhattan (or cab/grid) distance from this point to another point.
     * Note that Manhattan distance will not walk diagonally.
     * @param other Goal coordinate.
     * @return Distance in map units.
     */
    public int getManhattanDistance(Coordinate other) {
        int xDiff = x - other.x;
        int yDiff = y - other.y;
        return (xDiff * xDiff) + (yDiff * yDiff);
    }

    // TODO vector distance ?


    /**
     * Calculates this coordinate's position in the flattened
     * single array representation of the Map.
     * @param mapWidth The width of the map.
     * @param mapHeight The height of the map.
     * @return Position in the flattened array.
     */
    public int toPosition(int mapWidth, int mapHeight) {
        if (this.isOutOfBounds(mapWidth, mapHeight)) {
            throw new IllegalArgumentException("The coordinate must be within the bounds in order to convert to position");
        }
        return x + y * mapWidth;
    }

   public Direction getDirection(Coordinate other) {
        int deltaX = this.x - other.x;
        int deltaY = this.y - other.y;

        if (deltaY == 0) {
            if (deltaX == 1) return Direction.LEFT;
            else if (deltaX == -1) return Direction.RIGHT;
        } else if (deltaX == 0) {
            if (deltaY == 1) return Direction.DOWN;
            else if (deltaY == -1) return Direction.UP;
        }
        throw new IllegalArgumentException("Points must be neighboring " + this + " -> " +  other);
    }

    /**
     * @param deltaX amount to add to x-axis. TODO Positive values represent right or down. Negative values represent left or up.
     * @param deltaY amount to add to y-axis. Positive values represent right or down. Negative values represent left or up.
     * @return A new coordinate that is moved by the deltaX and deltaY.
     */
    public Coordinate translateByDelta(int deltaX, int deltaY) {
        return new Coordinate(x + deltaX, y + deltaY);
    }

    /**
     * @param direction Direction to move in.
     * @return A new coordinate that is moved by the given direction.
     */
    public Coordinate translateByDirection(Direction direction) {
        return switch (direction) {
            case LEFT -> new Coordinate(x - 1, y);
            case RIGHT -> new Coordinate(x + 1, y);
            case UP -> new Coordinate(x, y - 1);
            case DOWN -> new Coordinate(x, y + 1);
        };
    }



}

