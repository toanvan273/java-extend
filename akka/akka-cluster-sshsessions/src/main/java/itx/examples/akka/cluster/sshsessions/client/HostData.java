package itx.examples.akka.cluster.sshsessions.client;

import java.io.Serializable;

/**
 * Created by gergej on 25.3.2017.
 */
public class HostData implements Serializable {

    private String hostName;
    private int port;

    public HostData(String hostName, int port) {
        this.hostName = hostName;
        this.port = port;
    }

    public String getHostName() {
        return hostName;
    }

    public int getPort() {
        return port;
    }

}
