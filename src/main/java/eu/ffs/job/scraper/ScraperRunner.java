package eu.ffs.job.scraper;

import eu.ffs.repository.ConfigId;
import eu.ffs.repository.ConfigRepository;
import eu.ffs.repository.entity.DashboardConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.Optional;

/**
 * Created by j on 30.09.17.
 */
@Service
public class ScraperRunner {
    @Autowired
    MintosScraperJob mintosScraperJob;

    @Autowired
    TwinoScraperJob twinoScraperJob;

    @Autowired
    ViventorScraperJob viventorScraperJob;

    @Autowired
    ConfigRepository configRepository;

    @Scheduled(fixedRateString = "${ffs.import.interval}")
    public void perform() throws InterruptedException, MalformedURLException {
        Optional<DashboardConfiguration> configurationMintos = Optional.ofNullable(configRepository.findOne(ConfigId.AUTO_IMPORT_MINTOS));
        if (configurationMintos.isPresent() && configurationMintos.get().getBooleanValue()) {
            System.out.println("Running MintosScraper.");
            mintosScraperJob.perform();
        }
        Optional<DashboardConfiguration> configurationTwino = Optional.ofNullable(configRepository.findOne(ConfigId.AUTO_IMPORT_TWINO));
        if (configurationTwino.isPresent() && configurationTwino.get().getBooleanValue()) {
            System.out.println("Running TwinoScraper");
            twinoScraperJob.perform();
        }
        Optional<DashboardConfiguration> configurationViventor = Optional.ofNullable(configRepository.findOne(ConfigId.AUTO_IMPORT_VIVENTOR));
        if (configurationViventor.isPresent() && configurationViventor.get().getBooleanValue()) {
            System.out.println("Running ViventorScraper");
            viventorScraperJob.perform();
        }
    }
}
