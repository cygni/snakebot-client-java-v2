package se.cygni.snakebotclient.messages.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import se.cygni.snakebotclient.messages.SnakebotMessage;
import se.cygni.snakebotclient.model.GameSettings;
import se.cygni.snakebotclient.utils.enums.GameMode;
import se.cygni.snakebotclient.utils.enums.MessageType;

import java.util.UUID;

@Data
public class PlayerRegisteredResponse extends SnakebotMessage {
    private MessageType type = MessageType.PLAYER_REGISTERED;
    private UUID gameId;
    private String name;
    private GameSettings gameSettings;
    private GameMode gameMode;
    private UUID receivingPlayerId;
    private long timestamp;


    public static PlayerRegisteredResponse fromJson(String json) throws JsonProcessingException {
           return mapper.readValue(json, PlayerRegisteredResponse.class);
    }
}