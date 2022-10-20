package se.cygni.snakebotclient.utils;

import lombok.Data;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;
import se.cygni.snakebotclient.snakepit.Slippy;
import se.cygni.snakebotclient.snakepit.SnakeStrategy;
import se.cygni.snakebotclient.utils.enums.GameMode;


@Component
@Data
public class Configuration {

    // TODO Load from application.properties?

    private String host = "ws://localhost:8080/"; //snake.cygni.se/";
    private String venue = "TRAINING";
    private String name = "Slippy";

    private SnakeStrategy snake = new Slippy();
    private boolean spoiler = false;

    public Configuration(ApplicationArguments args) {
        for (String option : args.getOptionNames()) {
            switch (option) {
                case "host" -> this.host = "ws://" + args.getOptionValues(option).get(0);
                case "venue" -> this.venue = args.getOptionValues(option).get(0);
                case "name" -> this.name = args.getOptionValues(option).get(0);
                case "spoiler" -> this.spoiler = Boolean.parseBoolean(args.getOptionValues(option).get(0));
            }
        }

    }

}