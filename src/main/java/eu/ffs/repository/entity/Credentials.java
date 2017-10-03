package eu.ffs.repository.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by j on 30.09.17.
 */
@Entity
public class Credentials {
    @Id
    CredentialId credentialId;

    @Embedded
    CredentialToken credentialToken;

    public CredentialToken getCredentialToken() {
        return credentialToken;
    }

    public void setCredentialToken(CredentialToken credentialToken) {
        this.credentialToken = credentialToken;
    }

    public CredentialId getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(CredentialId credentialId) {
        this.credentialId = credentialId;
    }
}
