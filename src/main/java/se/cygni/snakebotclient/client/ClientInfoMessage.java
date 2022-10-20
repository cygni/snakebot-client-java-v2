package se.cygni.snakebotclient.client;

import lombok.Data;
import se.cygni.snakebotclient.messages.SnakebotMessage;

@Data
public class ClientInfoMessage extends SnakebotMessage {

    private String type = "se.cygni.snake.api.request.ClientInfo";
    private String clientVersion;
    private String clientLanguage = "Java";
    private String languageVersion = System.getProperty("java.version");
    private String operatingSystem;
    private String operatingSystemVersion;

    public ClientInfoMessage(String os, String osVersion, String applicationVersion) {
        this.operatingSystem = os;
        this.operatingSystemVersion = osVersion;
        this.clientVersion = applicationVersion;
    }

    public static ClientInfoMessage build() {
        String os = System.getProperty("os.name").toLowerCase();
        String osVersion = System.getProperty("os.version");
        String applicationVersion = "0.0.1"; // TODO
        return new ClientInfoMessage(os, osVersion, applicationVersion);

    }

}
