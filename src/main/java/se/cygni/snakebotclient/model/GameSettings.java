package se.cygni.snakebotclient.model;

import lombok.Data;

@Data
public class GameSettings {

    /** Maximum number of players in this game */
    private int maxNoofPlayers = 10;

    /** Maximum number of players in this game */
    private int startSnakeLength = 1;

    /** The time clients have to respond with a new move */
    private int timeInMsPerTick = 250;

    /** Randomly place obstacles */
    private boolean obstaclesEnabled = true;

    /** Randomly place food */
    private boolean foodEnabled = true;

    /** If a snake manages to nibble on the tail
     of another snake it will consume that tail part.
     I.e. the nibbling snake will grow one and
     victim will lose one. */
    private boolean headToTailConsumes = true;

    /** Only valid if headToTailConsumes is active.
     When tailConsumeGrows is set to true the
     consuming snake will grow when eating
     another snake. */
     private boolean tailConsumeGrows = false;

    /** Likelihood (in percent) that a new food will be
     added to the world */
    private int addFoodLikelihood = 15;

    /** Likelihood (in percent) that a
     food will be removed from the world */
    private int removeFoodLikelihood = 5;

    /** Snake grow every N world ticks.
     0 for disabled */
    private int spontaneousGrowthEveryNWorldTick = 3;

    /** Indicates that this is a training game,
     Bots will be added to fill up remaining players. */
    private boolean trainingGame = false;

    /** Points given per length unit the Snake has */
    private int pointsPerLength = 1;

    /** Points given per Food item consumed */
    private int pointsPerFood = 2;

    /** Points given per caused death (i.e. another
     snake collides with yours) */
    private int pointsPerCausedDeath = 5;

    /** Points given when a snake nibbles the tail
     of another snake */
    private int pointsPerNibble = 10;

    /** Number of rounds a tail is protected after nibble */
    private int noofRoundsTailProtectedAfterNibble = 3;

    /** The starting count for food */
    private int startFood = 10;

    /** The starting count for obstacles */
    private int startObstacles = 5;


    public static GameSettings customTraining() {
        GameSettings training = new GameSettings();
        return training;
    }
}
