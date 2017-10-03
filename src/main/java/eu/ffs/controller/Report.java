package eu.ffs.controller;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Report {
    private String capital;
    private BigDecimal capitalAsNum;
    private String interest;
    private BigDecimal interestAsNum;
    private String availableCash;
    private BigDecimal availableCashAsNum;
    private String investedAmount;
    private BigDecimal investedAmountAsNum;
    private String accountValue;
    private BigDecimal accountValueAsNum;

    public Report() {
        this.capitalAsNum = BigDecimal.ZERO;
        this.interestAsNum = BigDecimal.ZERO;
        this.availableCashAsNum= BigDecimal.ZERO;
        this.investedAmountAsNum = BigDecimal.ZERO;
        this.accountValueAsNum = BigDecimal.ZERO;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public BigDecimal getCapitalAsNum() {
        return capitalAsNum!=null?capitalAsNum:BigDecimal.ZERO;
    }

    public void setCapitalAsNum(BigDecimal capitalAsNum) {
        this.capitalAsNum = capitalAsNum;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public BigDecimal getInterestAsNum() {
        return interestAsNum!=null?interestAsNum:BigDecimal.ZERO;
    }

    public void setInterestAsNum(BigDecimal interestAsNum) {
        this.interestAsNum = interestAsNum;
    }

    public String getAvailableCash() {
        return availableCash;
    }

    public void setAvailableCash(String availableCash) {
        this.availableCash = availableCash;
    }

    public BigDecimal getAvailableCashAsNum() {
        return availableCashAsNum!=null?availableCashAsNum:BigDecimal.ZERO;
    }

    public void setAvailableCashAsNum(BigDecimal availableCashAsNum) {
        this.availableCashAsNum = availableCashAsNum;
    }

    public String getInvestedAmount() {
        return investedAmount;
    }

    public void setInvestedAmount(String investedAmount) {
        this.investedAmount = investedAmount;
    }

    public BigDecimal getInvestedAmountAsNum() {
        return investedAmountAsNum != null ? investedAmountAsNum : BigDecimal.ZERO;
    }

    public void setInvestedAmountAsNum(BigDecimal investedAmountAsNum) {
        this.investedAmountAsNum = investedAmountAsNum;
    }

    public String getAccountValue() {
        return accountValue;
    }

    public void setAccountValue(String accountValue) {
        this.accountValue = accountValue;
    }

    public BigDecimal getAccountValueAsNum() {
        return accountValueAsNum != null ? accountValueAsNum : BigDecimal.ZERO;
    }

    public void setAccountValueAsNum(BigDecimal accountValueAsNum) {
        this.accountValueAsNum = accountValueAsNum;
    }
}
