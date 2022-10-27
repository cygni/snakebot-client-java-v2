package se.cygni.snakebotclient.messages.request;

import se.cygni.snakebotclient.messages.GameMessage;
import se.cygni.snakebotclient.messages.MessageType;

public class HeartbeatRequest extends GameMessage {
    MessageType type = MessageType.HEARTBEAT_REQUEST;

}
