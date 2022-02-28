package org.com.travel.launch;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.File;
import java.text.DateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Base {
    public static WebDriver driver;
    public static JavascriptExecutor jse;

    public static WebDriver getWebDriver(String browserName) {
        switch (browserName.toLowerCase()) {
            case "chrome":
                return getChromeDriver();
            case "firefox":
                return getFirefoxDriver();
            case "internet explorer":
                return getInternetExplorerDriver();
            case "edge":
                return getEdgeDriver();
            default:
                throw new IllegalArgumentException("Match case not found for browser: "
                        + browserName);
        }
    }

    private static WebDriver getChromeDriver() {
//        System.setProperty("webdriver.chrome.driver", DRIVER_DIR + "chromedriver.exe");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return new ChromeDriver();
    }

    private static WebDriver getFirefoxDriver() {
//        System.setProperty("webdriver.gecko.driver", DRIVER_DIR + "geckodriver.exe");
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return new FirefoxDriver();
    }

    private static WebDriver getInternetExplorerDriver() {
//        System.setProperty("webdriver.ie.driver", DRIVER_DIR + "IEDriverServer.exe");
        WebDriverManager.iedriver().setup();
        driver = new InternetExplorerDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return new InternetExplorerDriver();
    }


    private static WebDriver getEdgeDriver() {
//        System.setProperty("webdriver.ie.driver", DRIVER_DIR + "IEDriverServer.exe");
        WebDriverManager.edgedriver().setup();
        driver = new EdgeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return new EdgeDriver();
    }


    public static void waitForPageLoad(WebDriver driver) {
        try {
            for (int i = 0; i < 60; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                if (jse.executeScript("return document.readyState").toString().equals("complete"))
                    break;
            }
        }
        catch(Exception e){
            System.out.println("Check the error in js ..." + e);
        }
        /*catch (JavascriptExecutor jse) {
            System.out.println("Check the error in js ..." + jse);
        }*/
    }

    public static String takeScreenshot(WebDriver driver,String fileName) throws Exception{

        TakesScreenshot scrShot = ((TakesScreenshot) driver);

        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        Date now = new Date();
        DateFormat shortDf= DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
        String currentDate= shortDf.format(now).toString().replace("/","_");
        currentDate = currentDate.toString().replace(" ","_");
        currentDate = currentDate.toString().replace(":","_");

        String dest = "./screenshots/" +fileName +"_" +currentDate+ ".png";
        File destination = new File(dest);
        FileUtils.copyFile(SrcFile,destination);
        return dest;

    }
}
