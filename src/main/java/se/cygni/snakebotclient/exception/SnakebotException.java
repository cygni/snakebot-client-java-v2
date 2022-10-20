package se.cygni.snakebotclient.exception;

public class SnakebotException extends Exception {
    public SnakebotException(String errorMsg, Exception e) {
        super(errorMsg, e);
    }
}
