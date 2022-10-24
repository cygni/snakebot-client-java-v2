package se.cygni.snakebotclient.messages.request;

import se.cygni.snakebotclient.messages.SnakebotMessage;
import se.cygni.snakebotclient.messages.MessageType;

public class HeartbeatRequest extends SnakebotMessage {
    MessageType type = MessageType.HEARTBEAT_REQUEST;

}
