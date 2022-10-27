package se.cygni.snakebotclient.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.cygni.snakebotclient.client.SnakebotService;
import se.cygni.snakebotclient.messages.event.*;
import se.cygni.snakebotclient.messages.message.InvalidMessage;

import java.util.UUID;

public class SnakebotTerminalUI implements SnakebotUI {

    private static final Logger logger = LoggerFactory.getLogger(SnakebotService.class.getName());
    @Override
    public void sendSnakeDeadAlert(UUID playerId, SnakeDeadEvent snakeDeadEvent) {
        if (snakeDeadEvent.getPlayerId().equals(playerId)) {
            logger.info("Your snake has died from {} at tick {}", snakeDeadEvent.getDeathReason(), snakeDeadEvent.getGameTick());
        } else {
            logger.info("Opponent {} has died from {} at tick {}",
                    snakeDeadEvent.getPlayerId(),
                    snakeDeadEvent.getDeathReason(),
                    snakeDeadEvent.getGameTick());
        }
    }

    @Override
    public void gameHasStarted(GameStartingEvent gameStartingEvent) {
        logger.info("Game is starting with {} players and settings: {}", gameStartingEvent.getNoofPlayers(), gameStartingEvent.getGameSettings());
    }

    @Override
    public void invalidMessageReceived(InvalidMessage invalidMessage) {
        logger.warn("{}: {} at {}", invalidMessage.getErrorMessage(), invalidMessage.getReceivedMessage(), invalidMessage.getTimestamp());
    }

    @Override
    public void gameHasEnded(GameEndedEvent gameEnded) {
        logger.info("Game Ended! Winner is: {}", gameEnded.getPlayerWinnerName());
    }

    @Override
    public void showGameResults(GameResultEvent gameResult) {
        logger.info("""
                Game Result:
                {}""", gameResult.getPlayerRanks());
    }

    @Override
    public void sendGameInfo(GameLinkEvent gameLinkEvent) {
        logger.info("Game is ready, view on: {}", gameLinkEvent.getUrl());
    }

}
