package eu.ffs.controller;

import eu.ffs.repository.ConfigRepository;
import eu.ffs.repository.MintosAccountEntryRepository;
import eu.ffs.repository.TwinoAccountEntryRepository;
import eu.ffs.repository.ViventorAccountEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DataController {

    @Autowired
    MintosAccountEntryRepository mintosAccountEntryRepository;

    @Autowired
    TwinoAccountEntryRepository twinoAccountEntryRepository;

    @Autowired
    ViventorAccountEntryRepository viventorAccountEntryRepository;

    @Autowired
    ConfigRepository configRepository;

    @RequestMapping("clearDB")
    public void clearDB() {
        mintosAccountEntryRepository.deleteAll();
        twinoAccountEntryRepository.deleteAll();
        viventorAccountEntryRepository.deleteAll();
        configRepository.deleteAll();
    }
}
