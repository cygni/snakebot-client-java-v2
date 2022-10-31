package se.cygni.snakebotclient.messages.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;
import se.cygni.snakebotclient.messages.GameMessage;
import se.cygni.snakebotclient.model.GameSettings;
import se.cygni.snakebotclient.model.enums.GameMode;
import se.cygni.snakebotclient.messages.MessageType;

import java.util.UUID;

@Getter
@Setter
public class PlayerRegisteredResponse extends GameMessage {
    private MessageType type = MessageType.PLAYER_REGISTERED;
    private String gameId;
    private String name;
    private GameSettings gameSettings;
    private GameMode gameMode;
    private UUID receivingPlayerId;
    private long timestamp;


    public static PlayerRegisteredResponse fromJson(String json) throws JsonProcessingException {
           return mapper.readValue(json, PlayerRegisteredResponse.class);
    }
}