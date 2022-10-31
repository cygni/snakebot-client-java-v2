package se.cygni.snakebotclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import se.cygni.snakebotclient.client.Snakebot;
import se.cygni.snakebotclient.model.GameSettings;

 @Component
public class SnakebotClient implements ApplicationRunner {

    private final SnakebotConfiguration configuration;

    @Autowired
    public SnakebotClient(SnakebotConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void run(ApplicationArguments args) {
        Snakebot.start(configuration, new GameSettings());

    }

}