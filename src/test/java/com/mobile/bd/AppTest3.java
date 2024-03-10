package com.mobile.bd;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.testng.annotations.AfterTest;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

//For install/uninstall app, add to cart and prove test
public class AppTest3 {
	public AndroidDriver driver;
    @BeforeTest
    public void setDevice() throws MalformedURLException {
        File f = new File("src/test/java\\resources");
        File apk = new File(f, "General-Store.apk");
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("appium:deviceName", "Batch22");
        desiredCapabilities.setCapability("platformName", "android");
        desiredCapabilities.setCapability("appium:automationName", "UiAutomator2");
        desiredCapabilities.setCapability("appium:appPackage", "com.androidsample.generalstore");
        desiredCapabilities.setCapability("appium:appActivity", "com.androidsample.generalstore.SplashActivity");
        desiredCapabilities.setCapability("appium:udid", "emulator-5554");
        desiredCapabilities.setCapability("app", apk.getAbsolutePath());
        URL remoteUrl = new URL("http://127.0.0.1:4723");
        driver = new AndroidDriver(remoteUrl, desiredCapabilities);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @Test
    public void test_app() throws InterruptedException {
        driver.findElement(By.id("android:id/text1")).click();
        String name = "Bangladesh";
        WebElement countryName = driver.findElement(AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\""+name+"\"))")) ;
        countryName.click();
        WebElement nameField = driver.findElement(By.id("com.androidsample.generalstore:id/nameField"));
        nameField.sendKeys("Hello world");
        driver.findElement(By.id("com.androidsample.generalstore:id/radioFemale")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement toolbarTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.androidsample.generalstore:id/toolbar_title")));
        Assert.assertEquals(toolbarTitle.getText(), "Products");
        driver.findElement(By.xpath("//*[@text='Air Jordan 4 Retro']/following-sibling::android.widget.LinearLayout/android.widget.TextView[@text=\"ADD TO CART\"]")).click();
        WebElement count = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.androidsample.generalstore:id/counterText")));
        Assert.assertEquals(count.getText(), "1");
        driver.findElement(By.id("com.androidsample.generalstore:id/appbar_btn_cart")).click();
        driver.findElement(By.className("android.widget.CheckBox")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnProceed")).click();
    }

    @AfterTest
    public void closeApp(){
    	driver.quit();
        //driver.removeApp("com.androidsample.generalstore");
    }
}
