package eu.ffs.controller;


/**
 * Created by Brosef on 21.04.2017.
 */
public class AggregatedAmountResponse {
    private String minBookingDate;
    private String maxBookingDate;
    private String aggregatedAmount;

    public String getAggregatedAmount() {
        return aggregatedAmount;
    }

    public void setAggregatedAmount(String aggregatedAmount) {
        this.aggregatedAmount = aggregatedAmount;
    }

    public String getMinBookingDate() {
        return minBookingDate;
    }

    public void setMinBookingDate(String minBookingDate) {
        this.minBookingDate = minBookingDate;
    }

    public String getMaxBookingDate() {
        return maxBookingDate;
    }

    public void setMaxBookingDate(String maxBookingDate) {
        this.maxBookingDate = maxBookingDate;
    }

}
