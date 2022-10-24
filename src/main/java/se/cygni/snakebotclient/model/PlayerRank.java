package se.cygni.snakebotclient.model;

import lombok.Data;

import java.util.UUID;

@Data
public class PlayerRank {
    private String playerName;
    private UUID playerId;
    private int rank;
    private int points;
    private boolean alive;
}
