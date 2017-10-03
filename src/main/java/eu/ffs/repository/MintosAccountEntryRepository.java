package eu.ffs.repository;

import eu.ffs.repository.entity.MintosAccountEntry;
import eu.ffs.repository.entity.pk.MintosAccountEntryPK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;

public interface MintosAccountEntryRepository extends CrudRepository<MintosAccountEntry, MintosAccountEntryPK>, AccountEntryRepository {

    // Mintos Search Expressions from Excel

    // funding
    String WITHDRAW_APPLICATION = "Withdraw application";
    String INCOMING_CLIENT_PAYMENT = "Incoming client payment";

    // interest
    String INTEREST_INCOME_LOAN = "Interest income Loan%";
    String DELAYED_INTEREST_INCOME_LOAN = "Delayed interest income Loan%";
    String INTEREST_INCOME_ON_REBUY = "Interest income on rebuy%";
    String DELAYED_INTEREST_INCOME_ON_REBUY = "Delayed interest income on rebuy%";

    // investment
    String INVESTMENT_PRINCIPAL_INCREASE_LOAN = "Investment principal increase Loan%";
    String INVESTMENT_PRINCIPAL_INCREASE = "Investment principal increase%";
    String INVESTMENT_PRINCIPAL_REBUY = "Investment principal rebuy%";
    String INVESTMENT_PRINCIPAL_REPAYMENT = "Investment principal repayment%";

    // Query Strings
    String SELECT_SUM = "select coalesce(sum(m.pk.turnover),0) from MintosAccountEntry m";

    String GET_FUNDED_AMOUNT_QRY = SELECT_SUM + " where m.pk.details = '" + INCOMING_CLIENT_PAYMENT + "' OR m.pk.details = '" + WITHDRAW_APPLICATION + "'";

    String GET_DEPOSITED_TOTAL_AMOUNT_QRY = SELECT_SUM + " where m.pk.details = '" + INCOMING_CLIENT_PAYMENT + "'";

    String GET_WITHDRAWED_TOTAL_AMOUNT_QRY = SELECT_SUM + " where m.pk.details = '" + WITHDRAW_APPLICATION + "'";

    String GET_INVESTED_AMOUNT = SELECT_SUM + " where m.pk.details like '" + INVESTMENT_PRINCIPAL_INCREASE_LOAN + "'"
            + " OR m.pk.details like '" + INVESTMENT_PRINCIPAL_INCREASE + "'"
            + " OR m.pk.details like '" + INVESTMENT_PRINCIPAL_REBUY + "'"
            + " OR m.pk.details like '" + INVESTMENT_PRINCIPAL_REPAYMENT + "'";

    String GET_ACCOUNT_VALUE_QRY = "select coalesce(sum(m.pk.turnover),0) from MintosAccountEntry m where lower(m.pk.details) like '%interest%' OR m.pk.details = '" + INCOMING_CLIENT_PAYMENT + "' OR m.pk.details = '" + WITHDRAW_APPLICATION + "'";

    @Query("select coalesce(sum(m.pk.turnover),0) from MintosAccountEntry m")
    BigDecimal getAvailableCash();

    @Query("select coalesce(sum(m.pk.turnover),0) from MintosAccountEntry m where lower(m.pk.details) like '%interest%'")
    BigDecimal getInterestAmount();

    @Query(GET_INVESTED_AMOUNT)
    BigDecimal getInvestedAmount();

    @Query(GET_ACCOUNT_VALUE_QRY)
    BigDecimal getAccountValue();

    @Query(GET_FUNDED_AMOUNT_QRY)
    BigDecimal getFundedAmount();

    @Query(GET_DEPOSITED_TOTAL_AMOUNT_QRY)
    BigDecimal getDepositedTotalAmount();

    @Query(GET_WITHDRAWED_TOTAL_AMOUNT_QRY)
    BigDecimal getWithdrawedTotalAmount();

}
