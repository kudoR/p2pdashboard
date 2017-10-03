package eu.ffs.repository.entity;

import eu.ffs.repository.entity.pk.TwinoAccountEntryPK;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.math.BigDecimal;

@Entity
public class TwinoAccountEntry extends AccountEntry {

    @EmbeddedId
    TwinoAccountEntryPK pk;

    public TwinoAccountEntry() {
        this.pk = new TwinoAccountEntryPK();
    }

    public TwinoAccountEntryPK getPk() {
        return pk;
    }

    public void setPk(TwinoAccountEntryPK pk) {
        this.pk = pk;
    }

    public void setAmount(BigDecimal amount) {
        this.pk.setAmount(amount);
    }

    public BigDecimal getAmount() {
        return this.pk.getAmount();
    }

    public void setLoanNumber(String loanNumber) {
        this.pk.setLoanNumber(loanNumber);
    }

    public String getLoanNumber() {
        return this.pk.getLoanNumber();
    }

    public String getProcessingDate() {
        return this.pk.getProcessingDate();
    }

    public void setProcessingDate(String processingDate) {
        this.pk.setProcessingDate(processingDate);
    }

    public String getBookingDate() {
        return this.pk.getBookingDate();
    }

    public void setBookingDate(String bookingDate) {
        this.pk.setBookingDate(bookingDate);
    }

    public String getType() {
        return this.pk.getType();
    }

    public void setType(String type) {
        this.pk.setType(type);
    }

    public String getDescription() {
        return this.pk.getDescription();
    }

    public void setDescription(String description) {
        this.pk.setDescription(description);
    }

    @Override
    public String toString() {
        return "TwinoAccountEntry{" +
                "processingDate='" + pk.getProcessingDate() + '\'' +
                ", bookingDate='" + pk.getBookingDate() + '\'' +
                ", type='" + pk.getType() + '\'' +
                ", description='" + pk.getDescription() + '\'' +
                ", loanNumber='" + pk.getLoanNumber() + '\'' +
                ", amount='" + pk.getAmount() + '\'' +
                '}';
    }
}
