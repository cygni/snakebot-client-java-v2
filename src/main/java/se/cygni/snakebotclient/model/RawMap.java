package se.cygni.snakebotclient.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class RawMap {
    private int width;
    private int height;
    private int worldTick;
    private ArrayList<SnakeInfo> snakes;
    private int[] foodPositions;
    private int[] obstaclePositions;

}
