package com.google.search;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class GoogleSearch {
    public WebDriver driver;
    public String url = "https://www.google.com";
    public String SearchText = "programming";

    @BeforeTest
    public void beforeTest() {
        SearchText = System.getProperty("searchText");
        String exePath = "chromedriver";
        System.setProperty("webdriver.chrome.driver", exePath);
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(url);
    }

    @Test
    public void googleSearch() {
        // search programming and submit
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys(SearchText);
        element.submit();
        List<WebElement> findElements;
        JavascriptExecutor jse = (JavascriptExecutor) driver;

        // Print title and sections and skip the google ads and maps part
        while (true) {
            findElements = driver.findElements(By.xpath("//*[@class='g']"));

            for (int j = 0; j < findElements.size(); j++) {
                System.out.println(findElements.get(j).getText());
                System.out.println("========================================================\n\n");
            }
            jse.executeScript("window.scrollBy(0,250)", "");
            if (driver.findElements(By.xpath("//span[. = \"Next\"]")).size() != 0) {
                driver.findElement(By.xpath("//span[. = \"Next\"]")).click();
            } else {
                break;
            }
        }
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
    }
}