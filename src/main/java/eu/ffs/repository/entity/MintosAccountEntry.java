package eu.ffs.repository.entity;

import eu.ffs.repository.entity.pk.MintosAccountEntryPK;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class MintosAccountEntry extends AccountEntry {
    @EmbeddedId
    MintosAccountEntryPK pk;

    public MintosAccountEntry() {
        this.pk = new MintosAccountEntryPK();
    }

    public MintosAccountEntryPK getPk() {
        return pk;
    }

    public String getTransaction() {
        return this.pk.getTransaction();
    }

    public void setTransaction(String transaction) {
        this.pk.setTransaction(transaction);
    }

    public String getDate() {
        return this.pk.getDate();
    }

    public void setDate(String date) {
        this.pk.setDate(date);
    }

    public String getDetails() {
        return this.pk.getDetails();
    }

    public void setDetails(String details) {
        this.pk.setDetails(details);
    }

    public BigDecimal getTurnover() {
        return this.pk.getTurnover();
    }

    public void setTurnover(BigDecimal turnover) {
        this.pk.setTurnover(turnover);
    }

    public BigDecimal getBalance() {
        return this.pk.getBalance();
    }

    public void setBalance(BigDecimal balance) {
        this.pk.setBalance(balance);
    }

    public String getCurrency() {
        return this.pk.getCurrency();
    }

    public void setCurrency(String currency) {
        this.pk.setCurrency(currency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MintosAccountEntry)) return false;

        MintosAccountEntry that = (MintosAccountEntry) o;

        return pk != null ? pk.equals(that.pk) : that.pk == null;
    }

    @Override
    public int hashCode() {
        return pk != null ? pk.hashCode() : 0;
    }
}
