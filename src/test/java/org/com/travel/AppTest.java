package org.com.travel;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.com.travel.launch.Base;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class AppTest extends Base {
    public static Logger log = LogManager.getLogger(Base.class.getName());

    @BeforeTest
    public void beforeTest() {
        driver = Base.getWebDriver("firefox");
        jse = ((JavascriptExecutor) driver);
        log.info("Edge Driver initialized successfully");
    }

    @AfterTest
    public void afterTest() {
        log.info("Test executed, Going to quit driver now");
        driver.quit();
    }

    @Test
    public void SearchFlights() throws Exception {
        driver.get("https://www.phptravels.net/login");
        Base.waitForPageLoad(driver);
        Assert.assertTrue(driver.getTitle().contains("Login - PHPTRAVELS"));
        log.info("Home page loaded successfully");

        driver.findElement(By.name("email")).sendKeys("user@phptravels.com");
        driver.findElement(By.name("password")).sendKeys("demouser");
        driver.findElement(By.xpath("//span[contains(text(),'Login')]")).click();
        Base.waitForPageLoad(driver);
        Assert.assertTrue(driver.getTitle().contains("Dashboard - PHPTRAVELS"));
        System.out.println("Dashboard page loaded successfully");


        driver.findElement(By.xpath("//a[contains(text(),flights)]")).click();
        Base.waitForPageLoad(driver);
        driver.findElement(By.name("from")).sendKeys(Keys.chord("indira",Keys.ARROW_DOWN,Keys.TAB));
        driver.findElement(By.name("to")).sendKeys(Keys.chord("changi",Keys.ARROW_DOWN,Keys.TAB));

        driver.findElement(By.className("next")).click();
        Base.waitForPageLoad(driver);
        driver.findElement(By.id("departure")).sendKeys(Keys.chord(Keys.CONTROL,"a"),Keys.DELETE,"20-03-2022",Keys.TAB);
        driver.findElement(By.xpath("//a[@class = 'dropdown-toggle dropdown-btn waves-effect']")).click();
        driver.findElement(By.xpath("//i[@class = 'la la-plus']")).click();
        driver.findElement(By.xpath("//i[@class ='mdi mdi-search']")).click();

        String scenarioName = Thread.currentThread().getStackTrace()[1].getMethodName();
        Base.takeScreenshot(driver,scenarioName);
    }
}
