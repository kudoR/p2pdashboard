package eu.ffs.processing;

import eu.ffs.repository.entity.AccountEntry;
import org.springframework.batch.item.ItemProcessor;

public class AccountEntryItemProcessor implements ItemProcessor<AccountEntry, AccountEntry>{
    @Override
    public AccountEntry process(AccountEntry accountEntry) throws Exception {
        return accountEntry;
    }
}
