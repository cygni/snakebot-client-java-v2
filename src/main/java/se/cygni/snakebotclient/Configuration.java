package se.cygni.snakebotclient;

import lombok.Data;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;
import se.cygni.snakebotclient.snake.Slippy;
import se.cygni.snakebotclient.snake.Snake;


@Component
@Data
public class Configuration {

    private String host = "https://snake.cygni.se";
    private String venue = "training";
    private String name = "Slippy";

    private Snake snake = new Slippy();
    private boolean spoiler = false;

    public Configuration(ApplicationArguments args) {
        for (String option : args.getOptionNames()) {
            switch (option) {
                case "host" -> this.host = args.getOptionValues(option).get(0);
                case "venue" -> this.venue = args.getOptionValues(option).get(0);
                case "name" -> this.name = args.getOptionValues(option).get(0);
                case "spoiler" -> this.spoiler = Boolean.parseBoolean(args.getOptionValues(option).get(0));
            }
        }
    }

}