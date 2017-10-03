package eu.ffs.repository.entity.pk;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Brosef on 26.04.2017.
 */
@Embeddable
public class MintosAccountEntryPK implements Serializable {
    private String transaction;
    private String date;
    private String details;
    @Column(name = "turnover", precision = 40, scale = 8)
    private BigDecimal turnover;
    @Column(name = "balance", precision = 40, scale = 8)
    private BigDecimal balance;
    private String currency;

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public BigDecimal getTurnover() {
        return turnover.setScale(8, BigDecimal.ROUND_HALF_UP);
    }

    public void setTurnover(BigDecimal turnover) {
        this.turnover = turnover.setScale(8, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getBalance() {
        return balance.setScale(8, BigDecimal.ROUND_HALF_UP);
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance.setScale(8, BigDecimal.ROUND_HALF_UP);
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public MintosAccountEntryPK() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MintosAccountEntryPK)) return false;

        MintosAccountEntryPK that = (MintosAccountEntryPK) o;

        if (transaction != null ? !transaction.equals(that.transaction) : that.transaction != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (details != null ? !details.equals(that.details) : that.details != null) return false;
        if (turnover != null ? turnover.compareTo(that.turnover) != 0 : that.turnover != null)
            return false;
        if (balance != null ? balance.compareTo(that.balance) != 0 : that.balance != null)
            return false;
        return currency != null ? currency.equals(that.currency) : that.currency == null;
    }

    @Override
    public int hashCode() {
        int result = transaction != null ? transaction.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (details != null ? details.hashCode() : 0);
        result = 31 * result + (turnover != null ? turnover.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }
}
