package eu.ffs.scraper;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;

public class ViventorScraper extends ScraperBase {


    public void getExport(String user, String pw) throws InterruptedException {

        if (!StringUtils.isEmpty(user) && !StringUtils.isEmpty(pw)) {

            driver.get("https://www.viventor.com/app/profile/account-statement");

            fillInput(By.id("email"), user);
            fillInput(By.id("password"), pw);
            waitUntilClickableAndThenClickOn(By.id("login"));
            waitUntilClickableAndThenClickOn(By.xpath("//button[contains(@ng-click,'$ctrl.exportStatement()')]"));

            Thread.sleep(2000);
        }
        driver.quit();


    }
}
