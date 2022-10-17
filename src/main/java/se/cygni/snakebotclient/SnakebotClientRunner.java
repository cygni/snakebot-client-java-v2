package se.cygni.snakebotclient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
public class SnakebotClientRunner implements ApplicationRunner {
    private static final Logger LOG = LoggerFactory.getLogger(SnakebotClientRunner.class);

    @Override
    public void run(ApplicationArguments args) {
        Configuration conf = new Configuration(args);
        LOG.info("Starting with configuration: {}", conf);

    }
}