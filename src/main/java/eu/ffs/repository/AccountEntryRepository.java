package eu.ffs.repository;

import java.math.BigDecimal;

/**
 * Created by Brosef on 27.04.2017.
 */
public interface AccountEntryRepository {
    BigDecimal getAvailableCash();
    BigDecimal getAccountValue();

    BigDecimal getInvestedAmount();
    BigDecimal getFundedAmount();

    BigDecimal getInterestAmount();
}
