package cn.wzy.regextest;

/**
 * @author wzy
 * @date 2018/8/16 15:35
 * @不短不长 八字刚好
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TestOne {
    private WebDriver driver;
    private String baseUrl;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.firefox.bin", "F:\\firefox\\firefox.exe");
        System.setProperty("webdriver.gecko.driver", "F:\\firefox\\geckodriver.exe");

        driver = new FirefoxDriver();
        baseUrl = "https://www.katalon.com/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void testUntitledTestCase() throws Exception {
        driver.get("http://www.jisuanqinet.com/shuxue/jinzhi.html");
        driver.findElement(By.id("sNum")).click();
        driver.findElement(By.id("sNum")).clear();
        driver.findElement(By.id("sNum")).sendKeys("13");
        driver.findElement(By.xpath("(.//*[normalize-space(text()) and normalize-space(.)='进制转换器'])[1]/following::input[2]")).click();
        driver.findElement(By.id("scale_2")).click();
        try {
            assertEquals("11201", driver.findElement(By.id("scale_2")).getAttribute("value"));
        } catch (Error e) {
            verificationErrors.append(e.toString());
        }
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
