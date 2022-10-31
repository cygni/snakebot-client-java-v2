package se.cygni.snakebotclient.messages.message;import lombok.Getter;
import lombok.Setter;
import se.cygni.snakebotclient.messages.GameMessage;
import se.cygni.snakebotclient.messages.MessageType;

@Getter
@Setter
public class ClientInfoMessage extends GameMessage {

    private MessageType type = MessageType.CLIENT_INFO;
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
