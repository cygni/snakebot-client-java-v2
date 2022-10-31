package se.cygni.snakebotclient.model;

import lombok.Getter;
import lombok.Setter;
import se.cygni.snakebotclient.messages.event.MapUpdateEvent;
import se.cygni.snakebotclient.model.enums.TileType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class GameState {
    private UUID playerId;
    private int width;
    private int height;

    private Map<UUID, Snake> snakes = new HashMap<>();
    private Map<Integer, TileType> tiles = new HashMap<>();

    public GameState(GameMap map, UUID receivingPlayerId) {
        map.getFoodPositions().forEach(p -> tiles.put(p, TileType.FOOD));
        map.getObstaclePositions().forEach(p -> tiles.put(p, TileType.OBSTACLE));
        map.getSnakeInfos().forEach(si -> {
            Snake snake = Snake.fromSnakeInfo(si, map.getWidth(), this);
            snakes.put(si.getId(), snake);
            si.getPositions().forEach(snakePos -> tiles.put(snakePos, TileType.SNAKE));
        });
        this.playerId = receivingPlayerId;
        this.width = map.getWidth();
        this.height = map.getHeight();

    }

    public static GameState fromMapUpdateEvent(MapUpdateEvent event) {
        return new GameState(event.getMap(), event.getReceivingPlayerId());
    }

    /**
     * Gets your snake
     * @return the Snake object belonging to you
     */
    public Snake getPlayerSnake() {
        return this.getSnakes().get(this.playerId);
    }

    public boolean freeTile(Coordinate next) {
        return getTileType(next) == TileType.EMPTY || getTileType(next) == TileType.FOOD;
    }

    public TileType getTileType(Coordinate coordinate) {
        if (coordinate.isOutOfBounds(this.width, this.height)) return TileType.OBSTACLE;

        int position = coordinate.toPosition(this.width, this.height);
        TileType type = this.tiles.get(position);
        if (type == null) return TileType.EMPTY;
        return type;
    }



}