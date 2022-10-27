package se.cygni.snakebotclient.view;

import se.cygni.snakebotclient.messages.event.*;
import se.cygni.snakebotclient.messages.message.InvalidMessage;

import java.util.UUID;

public interface SnakebotUI {
    void sendSnakeDeadAlert(UUID playerId, SnakeDeadEvent snakeDeadEvent);

    void gameHasStarted(GameStartingEvent gameStartingEvent);

    void invalidMessageReceived(InvalidMessage invalidMessage);

    void gameHasEnded(GameEndedEvent gameEnded);

    void showGameResults(GameResultEvent gameResult);

    void sendGameInfo(GameLinkEvent gameLinkEvent);
}
