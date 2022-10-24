package se.cygni.snakebotclient.model;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class SnakeInfo {
    private UUID id;
    private String name;
    private int points;
    private List<Integer> positions;
    private int tailProtectedForGameTicks;
}
