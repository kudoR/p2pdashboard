package eu.ffs.controller;

import eu.ffs.ConfigService;
import eu.ffs.job.scraper.ScraperRunner;
import eu.ffs.repository.ConfigId;
import eu.ffs.repository.ConfigRepository;
import eu.ffs.repository.CredentialRepository;
import eu.ffs.repository.entity.CredentialId;
import eu.ffs.repository.entity.CredentialToken;
import eu.ffs.repository.entity.Credentials;
import eu.ffs.repository.entity.DashboardConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Optional;

/**
 * Created by j on 06.05.17.
 */
@RestController
public class ConfigController {
    @Autowired
    ConfigRepository configRepository;

    @Autowired
    CredentialRepository credentialRepository;

    @Autowired
    ConfigService configService;

    @Autowired
    ScraperRunner scraperRunner;

    @RequestMapping("/getConfig")
    public Object getConfiguration() {
        Iterable<DashboardConfiguration> configurations = configRepository.findAll();
        HashMap<String, Boolean> configMap = new HashMap<>();
        for (DashboardConfiguration configuration : configurations) {
            configMap.put(configuration.getConfigurationId().name(), configuration.getBooleanValue());
        }
        return configMap;
    }

    @RequestMapping("getCredentialsSettings")
    public Object getCredentialsSettings() {
        HashMap<String, CredentialToken> credentialTokens = new HashMap<>();
        Iterable<Credentials> credentials = credentialRepository.findAll();
        credentials.forEach(c -> {
            credentialTokens.put(c.getCredentialId().name(), c.getCredentialToken());
        });
        return credentialTokens;
    }

    private void handleSaveSetting(ConfigId key, String value) {

        configService.updateInputPath(key, value);
    }

    @PostMapping("/saveScraperSettings")
    public ModelAndView handleSaveScraperSettings(
            @RequestParam(value = "twinoUser", required = false) String twinoUser,
            @RequestParam(value = "twinoPw", required = false) String twinoPw,
            @RequestParam(value = "mintosUser", required = false) String mintosUser,
            @RequestParam(value = "mintosPw", required = false) String mintosPw,
            @RequestParam(value = "viventorUser", required = false) String viventorUser,
            @RequestParam(value = "viventorPw", required = false) String viventorPw,
            @RequestParam(value = "twinoActive", required = false) Boolean twinoActive,
            @RequestParam(value = "mintosActive", required = false) Boolean mintosActive,
            @RequestParam(value = "viventorActive", required = false) Boolean viventorActive) throws MalformedURLException, InterruptedException {
        saveOrUpdateCredentials(CredentialId.TWINO, Optional.ofNullable(twinoUser), Optional.ofNullable(twinoPw));
        saveOrUpdateCredentials(CredentialId.MINTOS, Optional.ofNullable(mintosUser), Optional.ofNullable(mintosPw));
        saveOrUpdateCredentials(CredentialId.VIVENTOR, Optional.ofNullable(viventorUser), Optional.ofNullable(viventorPw));

        saveOrUpdateConfig(ConfigId.AUTO_IMPORT_MINTOS, Optional.empty(), Optional.ofNullable(mintosActive));
        saveOrUpdateConfig(ConfigId.AUTO_IMPORT_TWINO, Optional.empty(), Optional.ofNullable(twinoActive));
        saveOrUpdateConfig(ConfigId.AUTO_IMPORT_VIVENTOR, Optional.empty(), Optional.ofNullable(viventorActive));

        scraperRunner.perform();

        return new ModelAndView("redirect:/");

    }

    private void saveOrUpdateCredentials(CredentialId credentialId, Optional<String> user, Optional<String> pw) {
        if (user.isPresent() && pw.isPresent()) {
            Credentials credentials = credentialRepository.findOne(credentialId);
            if (credentials == null) {
                credentials = new Credentials();
                credentials.setCredentialId(credentialId);
            }
            CredentialToken credentialToken = new CredentialToken();
            credentialToken.setUser(user.get());
            credentialToken.setPassword(pw.get());
            credentials.setCredentialToken(credentialToken);
            credentialRepository.save(credentials);
        }
    }

    private void saveOrUpdateConfig(ConfigId configId, Optional<String> stringValue, Optional<Boolean> booleanValue) {
        DashboardConfiguration configuration = configRepository.findOne(configId);
        if (configuration == null) {
            configuration = new DashboardConfiguration(configId);
        }
        if (stringValue.isPresent()) {
            configuration.setStringValue(stringValue.get());
        }
        if (booleanValue.isPresent()) {
            configuration.setBooleanValue(booleanValue.get());
        } else {
            configuration.setBooleanValue(false);
        }
        configRepository.save(configuration);

    }

}
