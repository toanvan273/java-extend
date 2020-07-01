package itx.examples.akka.cluster.sshsessions.client;

import java.io.Serializable;

/**
 * Created by gergej on 25.3.2017.
 */
public class UserCredentials implements Serializable {

    private String userName;
    private String password;

    public UserCredentials(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

}
