package eu.ffs.processing;

import eu.ffs.repository.entity.AccountEntry;
import eu.ffs.repository.entity.MintosAccountEntry;
import eu.ffs.repository.entity.TwinoAccountEntry;
import eu.ffs.repository.MintosAccountEntryRepository;
import eu.ffs.repository.TwinoAccountEntryRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AccountEntryItemWriter<T> implements ItemWriter<AccountEntry> {
    @Autowired
    TwinoAccountEntryRepository twinoAccountEntryRepository;

    @Autowired
    MintosAccountEntryRepository mintosAccountEntryRepository;

    @Override
    public void write(List<? extends AccountEntry> list) throws Exception {
        for (AccountEntry accountEntry : list) {
            if (accountEntry instanceof TwinoAccountEntry) {
                twinoAccountEntryRepository.save((TwinoAccountEntry) accountEntry);
            }
            if (accountEntry instanceof MintosAccountEntry) {
                mintosAccountEntryRepository.save((MintosAccountEntry) accountEntry);
            }
        }
    }
}
