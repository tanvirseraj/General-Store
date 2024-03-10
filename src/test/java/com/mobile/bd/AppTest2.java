package com.mobile.bd;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import io.appium.java_client.android.AndroidDriver;

//For install app and uninstall app and prove test
public class AppTest2 {
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
        WebElement countryName = driver.findElement(By.xpath("//android.widget.TextView[@text='American Samoa']"));
        countryName.click();
        WebElement nameField = driver.findElement(By.id("com.androidsample.generalstore:id/nameField"));
        nameField.sendKeys("Hello world");
        driver.findElement(By.id("com.androidsample.generalstore:id/radioFemale")).click();
        driver.findElement(By.id("com.androidsample.generalstore:id/btnLetsShop")).click();
        Thread.sleep(3000);
        WebElement toolbarTitle = driver.findElement(By.id("com.androidsample.generalstore:id/toolbar_title"));
        Assert.assertEquals(toolbarTitle.getText(), "Products");

    }

    @AfterTest
    public void closeApp(){
    	driver.removeApp("com.androidsample.generalstore");
    }
}
