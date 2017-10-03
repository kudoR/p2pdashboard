package eu.ffs.job.scraper;

import eu.ffs.repository.CredentialRepository;
import eu.ffs.repository.entity.CredentialId;
import eu.ffs.repository.entity.CredentialToken;
import eu.ffs.repository.entity.Credentials;
import eu.ffs.scraper.TwinoScraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.MalformedURLException;
import java.util.Optional;

/**
 * Created by j on 16.09.17.
 */
@Service
public class TwinoScraperJob {

    @Autowired
    CredentialRepository credentialRepository;

    @Async
    public void perform() throws MalformedURLException, InterruptedException {
        Optional<Credentials> credentials = java.util.Optional.ofNullable(credentialRepository.findOne(CredentialId.TWINO));

        if (credentials.isPresent()) {
            CredentialToken credentialToken = credentials.get().getCredentialToken();
            String user = credentialToken.getUser();
            String password = credentialToken.getPassword();

            if (!StringUtils.isEmpty(user) && !StringUtils.isEmpty(password)) {
                new TwinoScraper().getExport(user, password);
            }
        }
    }
}
