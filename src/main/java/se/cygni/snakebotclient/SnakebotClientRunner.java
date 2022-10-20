package se.cygni.snakebotclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import se.cygni.snakebotclient.client.SnakebotClientService;
import se.cygni.snakebotclient.model.GameSettings;
import se.cygni.snakebotclient.snakepit.Slippy;
import se.cygni.snakebotclient.snakepit.SnakeStrategy;
import se.cygni.snakebotclient.utils.Configuration;
import se.cygni.snakebotclient.utils.enums.GameMode;


@Component
public class SnakebotClientRunner implements ApplicationRunner {
    private static final Logger logger = LoggerFactory.getLogger(SnakebotClientRunner.class);

    @Override
    public void run(ApplicationArguments args) {
        // TODO fix issues with Terminal CLI arguments
        Configuration conf = new Configuration(args);
        logger.info("Starting with configuration: {}", conf);


        String snakeChoice = "Slippy";
        // get snake implementation from snake name
        SnakeStrategy snake;
        switch (snakeChoice) {
            case "mysnake": snake = new Slippy();
            default:
                snake = new Slippy();
        }
        logger.info("Snake implementation: {}", snake);

        // Welcome user to snakebot
        logger.info("Welcome to snakebot {} and welcome to the snake pit", System.getProperty("user.name"));


        if (conf.getVenue().equals("TRAINING")) {
            logger.info("Overwriting training game settings with {}", GameSettings.defaultTraining());
        }

        String uri = conf.getHost();
        if (isArena(conf.getVenue())) {
            uri += "arena/" + conf.getVenue();
        } else {
            uri += conf.getVenue();
        }

        this.snakebotService = new SnakebotClientService(snake, uri, new GameSettings());


    }

    private static boolean isArena(String venue) {
        return !venue.toUpperCase().equals(GameMode.TRAINING.name()) && !venue.toUpperCase().equals(GameMode.TOURNAMENT.name());
    }
}