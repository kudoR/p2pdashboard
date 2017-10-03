package eu.ffs.repository;

import eu.ffs.repository.entity.ViventorAccountEntry;
import eu.ffs.repository.entity.pk.ViventorAccountEntryPK;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;


public interface ViventorAccountEntryRepository extends CrudRepository<ViventorAccountEntry, ViventorAccountEntryPK>, AccountEntryRepository {

    String TRANSACTION_TYPE_INTEREST_PAYMENT = "'Interest payment'";
    String TRANSACTION_TYPE_DISCOUNT_PREMIUM_FROM_PURCHASE_OF_LOAN = "'Discount/Premium from purchase of loan'";
    String TRANSACTION_TYPE_LATE_FEE_PAYMENT = "'Late fee payment'";

    String TRANSACTION_TYPE_INVESTMENT_IN_LOAN = "'Investment in loan'";
    String TRANSACTION_TYPE_SHARE_SOLD = "'Share sold'";
    String TRANSACTION_TYPE_PRINCIPAL_REPAYMENT = "'Principal repayment'";
    String TRANSACTION_TYPE_SHARE_BOUGHT = "'Share bought'";

    String TRANSACTION_TYPE_FUNDS_DEPOSIT = "'Funds deposit'";
    String TRANSACTION_TYPE_FUNDS_WITHDRAWAL = "'Funds withdrawal'";

    String SELECT = "select coalesce(sum(v.pk.turnover),0) from ViventorAccountEntry v";

    String WHERE_EARNINGS = " where v.pk.typeOfTransaction in ("
            + TRANSACTION_TYPE_DISCOUNT_PREMIUM_FROM_PURCHASE_OF_LOAN + ","
            + TRANSACTION_TYPE_INTEREST_PAYMENT + ","
            + TRANSACTION_TYPE_LATE_FEE_PAYMENT
            + ")";

    String WHERE_INVESTINGS = " where v.pk.typeOfTransaction in ("
            + TRANSACTION_TYPE_INVESTMENT_IN_LOAN + ","
            + TRANSACTION_TYPE_PRINCIPAL_REPAYMENT + ","
            + TRANSACTION_TYPE_SHARE_BOUGHT + ","
            + TRANSACTION_TYPE_SHARE_SOLD
            + ")";

    String WHERE_VALUE = " where v.pk.typeOfTransaction in ("
            + TRANSACTION_TYPE_DISCOUNT_PREMIUM_FROM_PURCHASE_OF_LOAN + ","
            + TRANSACTION_TYPE_FUNDS_DEPOSIT + ","
            + TRANSACTION_TYPE_FUNDS_WITHDRAWAL + ","
            + TRANSACTION_TYPE_INTEREST_PAYMENT + ","
            + TRANSACTION_TYPE_LATE_FEE_PAYMENT
            + ")";

    String WHERE_AMOUNT = " where v.pk.typeOfTransaction in ("
            + TRANSACTION_TYPE_FUNDS_DEPOSIT + ","
            + TRANSACTION_TYPE_FUNDS_WITHDRAWAL
            + ")";


    String GET_INTEREST_AMOUNT_QRY = SELECT + WHERE_EARNINGS;
    String GET_INVESTED_AMOUNT_QRY = SELECT + WHERE_INVESTINGS;
    String GET_ACCOUNT_VALUE_QRY = SELECT + WHERE_VALUE;
    String GET_FUNDED_AMOUNT_QRY = SELECT + WHERE_AMOUNT;

    @Query(SELECT)
    BigDecimal getAvailableCash();

    @Query(GET_INTEREST_AMOUNT_QRY)
    BigDecimal getInterestAmount();

    @Query(GET_INVESTED_AMOUNT_QRY)
    BigDecimal getInvestedAmount();

    @Query(GET_ACCOUNT_VALUE_QRY)
    BigDecimal getAccountValue();

    @Query(GET_FUNDED_AMOUNT_QRY)
    BigDecimal getFundedAmount();
}
