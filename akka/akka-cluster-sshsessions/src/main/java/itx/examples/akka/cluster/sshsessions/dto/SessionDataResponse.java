package itx.examples.akka.cluster.sshsessions.dto;

import java.io.Serializable;

/**
 * Created by juraj on 3/18/17.
 */
public class SessionDataResponse implements Serializable {

    private String data;

    public SessionDataResponse(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

}
