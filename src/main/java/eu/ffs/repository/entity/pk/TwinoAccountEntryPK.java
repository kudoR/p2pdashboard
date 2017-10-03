package eu.ffs.repository.entity.pk;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created by Brosef on 21.04.2017.
 */
@Embeddable
public class TwinoAccountEntryPK implements Serializable {
    private String processingDate;
    private String bookingDate;
    private String type;
    private String description;
    private String loanNumber;
    @Column(name = "amount", precision = 20, scale = 4)
    private BigDecimal amount;

    public String getProcessingDate() {
        return processingDate;
    }

    public void setProcessingDate(String processingDate) {
        this.processingDate = processingDate;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
    }

    public BigDecimal getAmount() {
        return amount.setScale(4, BigDecimal.ROUND_HALF_UP);
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount.setScale(4, BigDecimal.ROUND_HALF_UP);
    }

    public TwinoAccountEntryPK() {
    }

}
