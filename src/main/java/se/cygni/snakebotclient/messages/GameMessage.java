package se.cygni.snakebotclient.messages;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public abstract class GameMessage {
    private MessageType type;
    protected static final ObjectMapper mapper = new ObjectMapper();

    public String toJson() throws JsonProcessingException {
        return mapper.writeValueAsString(this);
    }

}
