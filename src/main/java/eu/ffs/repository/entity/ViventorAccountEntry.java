package eu.ffs.repository.entity;

import eu.ffs.repository.entity.pk.ViventorAccountEntryPK;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class ViventorAccountEntry extends AccountEntry {
    @EmbeddedId
    ViventorAccountEntryPK pk;

    public ViventorAccountEntry() {
        this.pk = new ViventorAccountEntryPK();
    }

    public ViventorAccountEntryPK getPk() {
        return pk;
    }

    public String getNumber() {
        return this.pk.getNumber();
    }

    public void setNumber(String number) {
        this.pk.setNumber(number);
    }

    public String getDate() {
        return this.pk.getDate();
    }

    public void setDate(String date) {
        this.pk.setDate(date);
    }

    public String getTypeOfTransaction() {
        return this.pk.getTypeOfTransaction();
    }

    public void setTypeOfTransaction(String typeOfTransaction) {
        this.pk.setTypeOfTransaction(typeOfTransaction);
    }

    public String getDescriptionOfTransaction() {
        return this.pk.getDescriptionOfTransaction();
    }

    public void setDescriptionOfTransaction(String descriptionOfTransaction) {
        this.pk.setDescriptionOfTransaction(descriptionOfTransaction);
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

}
