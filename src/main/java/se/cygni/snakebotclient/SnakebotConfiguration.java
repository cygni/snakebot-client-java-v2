package se.cygni.snakebotclient;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.cygni.snakebotclient.model.SnakeStrategy;
import se.cygni.snakebotclient.model.enums.GameMode;
import se.cygni.snakebotclient.snakepit.MySnake;
import se.cygni.snakebotclient.snakepit.Slippy;

@Component
@Getter
@Setter
@ToString
public class SnakebotConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(SnakebotConfiguration.class);

    private String name;
    private String snakeChoice;
    private String venue;
    private String host;

    @Autowired
    public SnakebotConfiguration(@Value("${snakebot.name}") String name,
                                 @Value("${snakebot.snake-choice}") String snakeChoice,
                                 @Value("${snakebot.venue}") String venue,
                                 @Value("${snakebot.host}") String host)
    {
        this.name = name;
        this.snakeChoice = snakeChoice;
        this.venue = venue;
        this.host = host;
    }


    public SnakeStrategy getSnakeStrategy() {
        SnakeStrategy snake;
        switch (this.snakeChoice) {
            case "mysnake" -> snake = new MySnake();
            // TODO: you can add more options here
            default -> {
                logger.info("Unknown snake choice: " + this.snakeChoice + ", using default");
                snake = new Slippy();
            }
        }
        return snake;
    }

    public String getUri() {
        String uri = this.host;
        if (isArena(this.venue)) {
            uri += "arena/" + this.venue;
        } else {
            uri += this.venue;
        }
        return uri;
    }

    private static boolean isArena(String venue) {
        return !venue.toUpperCase().equals(GameMode.TRAINING.name()) && !venue.toUpperCase().equals(GameMode.TOURNAMENT.name());
    }
}
