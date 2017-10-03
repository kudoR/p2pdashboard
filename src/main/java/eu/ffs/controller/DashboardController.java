package eu.ffs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;


@RestController
public class DashboardController {

    @Autowired
    TwinoDataController twinoDataController;

    @Autowired
    MintosDataController mintosDataController;

    @Autowired
    ViventorDataController viventorDataController;

    @Autowired
    ConfigurableApplicationContext configurableApplicationContext;

    @RequestMapping("/getReport")
    public DashboardReport getReport() {
        DashboardReport dashboardReport = new DashboardReport();

        Report twinoReport = twinoDataController.getReport();
        dashboardReport.setTwinoReport(twinoReport);

        Report mintosReport = mintosDataController.getReport();
        dashboardReport.setMintosReport(mintosReport);

        Report viventorReport = viventorDataController.getReport();
        dashboardReport.setViventorReport(viventorReport);

        // Sum
        Report sumReport = new Report();
        Locale german = new Locale("de", "DE");
        NumberFormat germanFormat = NumberFormat.getCurrencyInstance(german);
        sumReport.setAccountValue(germanFormat.format(getAccountValueSum(twinoReport, mintosReport, viventorReport)));
        sumReport.setInvestedAmount(germanFormat.format(getInvestedAmountSum(twinoReport, mintosReport, viventorReport)));
        sumReport.setAvailableCash(germanFormat.format(getAvailableCashSum(twinoReport, mintosReport, viventorReport)));
        sumReport.setInterest(germanFormat.format(getInterestSum(twinoReport, mintosReport, viventorReport)));
        sumReport.setCapital(germanFormat.format(getCapitalSum(twinoReport, mintosReport, viventorReport)));

        dashboardReport.setSumReport(sumReport);

        return dashboardReport;
    }

    private BigDecimal getCapitalSum(Report... reports) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Report r : reports) {
            sum = sum.add(r.getCapitalAsNum());
        }

        return sum;
    }

    private BigDecimal getInterestSum(Report... reports) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Report r : reports) {
            sum = sum.add(r.getInterestAsNum());
        }

        return sum;
    }

    @RequestMapping("/shutdown")
    public void shutdown() {
        System.out.println("Received Shutdown request. Shutting down.");
        configurableApplicationContext.close();
    }

    private BigDecimal getAvailableCashSum(Report... reports) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Report r : reports) {
            sum = sum.add(r.getAvailableCashAsNum());
        }

        return sum;
    }

    private BigDecimal getInvestedAmountSum(Report... reports) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Report r : reports) {
            sum = sum.add(r.getInvestedAmountAsNum());
        }

        return sum;
    }

    private BigDecimal getAccountValueSum(Report... reports) {
        BigDecimal sum = BigDecimal.ZERO;
        for (Report r : reports) {
            sum = sum.add(r.getAccountValueAsNum());
        }

        return sum;
    }
}
