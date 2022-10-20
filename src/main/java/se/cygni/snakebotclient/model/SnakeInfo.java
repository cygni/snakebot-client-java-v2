package se.cygni.snakebotclient.model;

import lombok.Data;

@Data
public class SnakeInfo {
    private String id;
    private String name;
    private int points;
    private int[] positions; // ?
    private int tailProtectionDuration;
}
