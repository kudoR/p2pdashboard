package eu.ffs.controller;

import eu.ffs.repository.AccountEntryRepository;
import org.springframework.batch.item.excel.RowMapper;
import org.springframework.batch.item.excel.mapping.BeanWrapperRowMapper;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Brosef on 27.04.2017.
 */
public abstract class BaseDataController<T> {


    public static Report getReportFromRepo(AccountEntryRepository accountEntryRepository) {
        Locale german = new Locale("de", "DE");
        NumberFormat germanFormat = NumberFormat.getCurrencyInstance(german);

        Report report = new Report();
        BigDecimal funding = accountEntryRepository.getFundedAmount();
        report.setCapital(germanFormat.format(funding));
        report.setCapitalAsNum(funding);

        BigDecimal interest = accountEntryRepository.getInterestAmount();
        report.setInterest(germanFormat.format(interest));
        report.setInterestAsNum(interest);

        BigDecimal availableCash = accountEntryRepository.getAvailableCash();
        report.setAvailableCash(germanFormat.format(availableCash));
        report.setAvailableCashAsNum(availableCash);

        BigDecimal accountValue = accountEntryRepository.getAccountValue();
        report.setAccountValue(germanFormat.format(accountValue));
        report.setAccountValueAsNum(accountValue);

        BigDecimal investedAmount = accountEntryRepository.getInvestedAmount();
        report.setInvestedAmount(germanFormat.format(investedAmount));
        report.setInvestedAmountAsNum(investedAmount);

        return report;
    }

    protected RowMapper<T> genericRowMapper() {
        BeanWrapperRowMapper<T> rowMapper = new BeanWrapperRowMapper<>();
        Class targetType = getTargetType();
        rowMapper.setTargetType(targetType);
        return rowMapper;
    }

    public abstract Class getTargetType();
}
