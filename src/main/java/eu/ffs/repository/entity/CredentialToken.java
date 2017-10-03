package eu.ffs.repository.entity;

import java.io.Serializable;

/**
 * Created by j on 30.09.17.
 */
public class CredentialToken implements Serializable {
    String user;
    String password;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
