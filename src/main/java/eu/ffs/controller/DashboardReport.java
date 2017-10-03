package eu.ffs.controller;

/**
 * Created by Brosef on 27.04.2017.
 */
public class DashboardReport {
    Report twinoReport;
    Report mintosReport;
    Report viventorReport;
    Report sumReport;

    public Report getViventorReport() {
        return viventorReport;
    }

    public void setViventorReport(Report viventorReport) {
        this.viventorReport = viventorReport;
    }

    public Report getTwinoReport() {
        return twinoReport;
    }

    public void setTwinoReport(Report twinoReport) {
        this.twinoReport = twinoReport;
    }

    public Report getMintosReport() {
        return mintosReport;
    }

    public void setMintosReport(Report mintosReport) {
        this.mintosReport = mintosReport;
    }

    public Report getSumReport() {
        return sumReport;
    }

    public void setSumReport(Report sumReport) {
        this.sumReport = sumReport;
    }
}
