package se.cygni.snakebotclient.model;

import lombok.Data;

@Data
public class GameSettings {

    /** Maximum number of players in this game */
    private int maxNoofPlayers;

    /** Maximum number of players in this game */
    private int startSnakeLength;

    /** The time clients have to respond with a new move */
    private int timeInMsPerTick;

    /** Randomly place obstacles */
    private boolean obstaclesEnabled;

    /** Randomly place food */
    private boolean foodEnabled;

    /** If a snake manages to nibble on the tail
     of another snake it will consume that tail part.
     I.e. the nibbling snake will grow one and
     victim will lose one. */
    private boolean headToTailConsumes;

    /** Only valid if headToTailConsumes is active.
     When tailConsumeGrows is set to true the
     consuming snake will grow when eating
     another snake. */
     private boolean tailConsumeGrows;

    /** Likelihood (in percent) that a new food will be
     added to the world */
    private int addFoodLikelihood;

    /** Likelihood (in percent) that a
     food will be removed from the world */
    private int removeFoodLikelihood;

    /** Snake grow every N world ticks.
     0 for disabled */
    private int spontaneousGrowthEveryNWorldTick;

    /** Indicates that this is a training game,
     Bots will be added to fill up remaining players. */
    private boolean trainingGame;

    /** Points given per length unit the Snake has */
    private int pointsPerLength;

    /** Points given per Food item consumed */
    private int pointsPerFood;

    /** Points given per caused death (i.e. another
     snake collides with yours) */
    private int pointsPerCausedDeath;

    /** Points given when a snake nibbles the tail
     of another snake */
    private int pointsPerNibble;

    /** Number of rounds a tail is protected after nibble */
    private int noofRoundsTailProtectedAfterNibble;

    /** The starting count for food */
    private int startFood;

    /** The starting count for obstacles */
    private int startObstacles;


    public static GameSettings defaultTraining() {
        GameSettings training = new GameSettings();
        training.addFoodLikelihood = 1;
        // TODO!
        return training;
    }
}
