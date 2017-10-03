package eu.ffs.repository;

import eu.ffs.repository.entity.AccountEntry;
import eu.ffs.repository.entity.MintosAccountEntry;
import eu.ffs.repository.entity.TwinoAccountEntry;
import eu.ffs.repository.entity.ViventorAccountEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CentralRepositoryService {

    @Autowired
    TwinoAccountEntryRepository twinoAccountEntryRepository;

    @Autowired
    MintosAccountEntryRepository mintosAccountEntryRepository;

    @Autowired
    ViventorAccountEntryRepository viventorAccountEntryRepository;

    public void saveAccountEntry(AccountEntry accountEntry) {
        if (accountEntry instanceof MintosAccountEntry) {
            MintosAccountEntry mintosAccountEntry = (MintosAccountEntry) accountEntry;

            if (!mintosAccountEntryRepository.exists(mintosAccountEntry.getPk())) {
                mintosAccountEntryRepository.save(mintosAccountEntry);
            }
        }

        if (accountEntry instanceof TwinoAccountEntry) {
            TwinoAccountEntry twinoAccountEntry = (TwinoAccountEntry) accountEntry;

            if (!twinoAccountEntryRepository.exists(twinoAccountEntry.getPk())) {
                twinoAccountEntryRepository.save(twinoAccountEntry);
            }
        }

        if (accountEntry instanceof ViventorAccountEntry) {
            ViventorAccountEntry viventorAccountEntry = (ViventorAccountEntry) accountEntry;

            if (isValidAccountEntry(viventorAccountEntry)) {

                if (!viventorAccountEntryRepository.exists(viventorAccountEntry.getPk())) {
                    viventorAccountEntryRepository.save(viventorAccountEntry);
                }

            }
        }

    }

    private boolean isValidAccountEntry(ViventorAccountEntry viventorAccountEntry) {
        if (viventorAccountEntry.getBalance() == null) {
            return false;
        }
        return true;
    }
}
