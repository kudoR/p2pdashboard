package eu.ffs.repository.entity.pk;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Brosef on 26.04.2017.
 */
@Embeddable
public class ViventorAccountEntryPK implements Serializable {

    private String number;
    private String date;
    private String typeOfTransaction;
    private String descriptionOfTransaction;
    @Column(name = "turnover", precision = 20, scale = 2)
    private BigDecimal turnover;
    private BigDecimal balance;

    public ViventorAccountEntryPK() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViventorAccountEntryPK that = (ViventorAccountEntryPK) o;

        if (number != null ? !number.equals(that.number) : that.number != null) return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        if (typeOfTransaction != null ? !typeOfTransaction.equals(that.typeOfTransaction) : that.typeOfTransaction != null)
            return false;
        if (descriptionOfTransaction != null ? !descriptionOfTransaction.equals(that.descriptionOfTransaction) : that.descriptionOfTransaction != null)
            return false;
        if (turnover != null ? !turnover.equals(that.turnover) : that.turnover != null) return false;
        return balance != null ? balance.equals(that.balance) : that.balance == null;
    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (typeOfTransaction != null ? typeOfTransaction.hashCode() : 0);
        result = 31 * result + (descriptionOfTransaction != null ? descriptionOfTransaction.hashCode() : 0);
        result = 31 * result + (turnover != null ? turnover.hashCode() : 0);
        result = 31 * result + (balance != null ? balance.hashCode() : 0);
        return result;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTypeOfTransaction() {
        return typeOfTransaction;
    }

    public void setTypeOfTransaction(String typeOfTransaction) {
        this.typeOfTransaction = typeOfTransaction;
    }

    public String getDescriptionOfTransaction() {
        return descriptionOfTransaction;
    }

    public void setDescriptionOfTransaction(String descriptionOfTransaction) {
        this.descriptionOfTransaction = descriptionOfTransaction;
    }

    public BigDecimal getTurnover() {
        return turnover.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public void setTurnover(BigDecimal turnover) {
        this.turnover = turnover.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
