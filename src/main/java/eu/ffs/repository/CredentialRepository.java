package eu.ffs.repository;

import eu.ffs.repository.entity.CredentialId;
import eu.ffs.repository.entity.Credentials;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by j on 05.05.17.
 */
public interface CredentialRepository extends CrudRepository<Credentials, CredentialId> {
}
