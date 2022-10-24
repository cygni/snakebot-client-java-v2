package se.cygni.snakebotclient.model;

import lombok.Data;

import java.util.List;

@Data
public class GameMap {
    private int width;
    private int height;
    private int worldTick;
    private List<SnakeInfo> snakeInfos;
    private List<Integer> foodPositions;
    private List<Integer> obstaclePositions;

}
