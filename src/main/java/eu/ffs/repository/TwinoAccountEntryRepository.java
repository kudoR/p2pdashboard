package eu.ffs.repository;

import eu.ffs.repository.entity.TwinoAccountEntry;
import eu.ffs.repository.entity.pk.TwinoAccountEntryPK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;

public interface TwinoAccountEntryRepository extends CrudRepository<TwinoAccountEntry, TwinoAccountEntryPK>, AccountEntryRepository  {

    List<TwinoAccountEntry> findByPkType(String type);
    List<TwinoAccountEntry> findByPkDescription(String type);

    String SELECT = "select coalesce(sum(t.pk.amount),0) from TwinoAccountEntry t";

    // query
    String FUNDED_AMOUNT_QRY = SELECT + " where t.pk.type='FUNDING'";
    String INVESTED_AMOUNT_QRY = SELECT + " where t.pk.description not in ('INTEREST') and t.pk.type not in ('FUNDING')";
    String ACCOUNT_VALUE_QRY = SELECT + " where t.pk.description not in ('PRINCIPAL')";
    String INTEREST_AMOUNT_QRY = SELECT + " where t.pk.description='INTEREST'";

    @Query(SELECT)
    BigDecimal getAvailableCash();

    @Query(ACCOUNT_VALUE_QRY)
    BigDecimal getAccountValue();

    @Query(INVESTED_AMOUNT_QRY)
    BigDecimal getInvestedAmount();

    @Query(FUNDED_AMOUNT_QRY)
    BigDecimal getFundedAmount();

    @Query(INTEREST_AMOUNT_QRY)
    BigDecimal getInterestAmount();
}
