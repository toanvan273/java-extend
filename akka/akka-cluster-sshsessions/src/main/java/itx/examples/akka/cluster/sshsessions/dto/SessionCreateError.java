package itx.examples.akka.cluster.sshsessions.dto;

/**
 * Created by gergej on 25.3.2017.
 */
public class SessionCreateError {

    private String clientId;

    public SessionCreateError(String clientId) {
        this.clientId = clientId;
    }

    public String getClientId() {
        return clientId;
    }

}
