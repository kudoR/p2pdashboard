package eu.ffs.scraper;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.File;
import java.io.IOException;

public class TwinoScraper extends ScraperBase {

    public void getExport(String user, String pw) {
        try {
            System.out.println("Starting TwinoScraper");

            if (!StringUtils.isEmpty(user) && !StringUtils.isEmpty(pw)) {

                driver.get("https://www.twino.eu/de/");

                this.clickOnElemNotInViewport(By.className("js-login-btn"));

                By username_selector = By.className("login__inputText");
                wait.until(ExpectedConditions.visibilityOfElementLocated(username_selector));

                WebElement username = driver.findElement(username_selector);
                username.clear();
                username.sendKeys(user);

                WebElement password = driver.findElement(By.className("login__inputPass"));
                password.clear();

                password.sendKeys(pw);

                clickOnElemNotInViewport(By.className("login__btn"));
                Thread.sleep(5000);

                driver.get("https://www.twino.eu/de/profile/investor/my-investments/account-transactions");

                Thread.sleep(2000);

                clickOnElemNotInViewport(By.className("accStatement__pdf"));
                Thread.sleep(1000);
                System.out.println(6);
            } else {
                System.out.println("No Username and/or Password provided for TwinoScraper!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(scrFile, new File("/home/j/Bilder/errorscreen.png"));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Could not take Screenshot.");
            }
            driver.quit();
        }

    }

}
