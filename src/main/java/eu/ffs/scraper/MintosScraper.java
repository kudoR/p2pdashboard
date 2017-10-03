package eu.ffs.scraper;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MintosScraper extends ScraperBase {


    public void getExport(String user, String pw) throws InterruptedException {

        if (!StringUtils.isEmpty(user) && !StringUtils.isEmpty(pw)) {
            driver.get("http://www.mintos.com/de");

            WebElement myAccountButton = driver.findElement(By.name("MyAccountButton"));
            myAccountButton.click();

            System.out.println(":::::::::: [ clicked MyAccountButton ] ::::::::::");

            By username_selector = By.name("_username");
            wait.until(ExpectedConditions.visibilityOfElementLocated(username_selector));

            WebElement username = driver.findElement(username_selector);
            username.clear();
            username.sendKeys(user);

            WebElement password = driver.findElement(By.name("_password"));
            password.clear();

            password.sendKeys(pw);

            waitUntilVisibleAndThenClickOn(By.className("account-login-btn"));
            System.out.println(":::::::::: [ loginButton clicked ] ::::::::::");

            driver.get("http://www.mintos.com/de/kontoauszug");

            waitUntilVisibleAndThenClickOn(By.linkText("Letzter Monat und dieser Monat"));
            System.out.println(":::::::::: [ Letzter Monat und dieser Monat clicked ] ::::::::::");

            waitUntilVisibleAndThenClickOn(By.id("export-button"));
            System.out.println(":::::::::: [ Export clicked ] ::::::::::");

//            confirmDownloadwindow();
        } else {
            System.out.println("No Username and/or Password provided for MintosScraper!");
        }
        driver.quit();

    }

}
