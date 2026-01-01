package com.videoautomation.tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.io.FileInputStream;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Unified BaseTest for TV Platforms.
 * Note: Roku is handled via HTTP Client wrapper usually, but here we structure
 * it to define the flow.
 */
public class BaseTest {

    protected static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    protected Properties props;
    protected static Logger log = Logger.getLogger(BaseTest.class.getName());

    // For Roku, we might use a separate helper since it's not strictly WebDriver
    // based in all cases
    protected String rokuIp;

    public WebDriver getDriver() {
        return driver.get();
    }

    @BeforeMethod
    @Parameters({ "platformName" })
    public void setup(String platformName) throws Exception {
        props = new Properties();
        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
        props.load(fis);

        String platform = platformName != null ? platformName : props.getProperty("platform");
        log.info("Setting up driver for TV Platform: " + platform);

        if (platform.equalsIgnoreCase("androidtv")) {
            UiAutomator2Options options = new UiAutomator2Options();
            options.setPlatformName("Android");
            options.setDeviceName(props.getProperty("androidTvDeviceName"));
            options.setAppPackage(props.getProperty("androidTvAppPackage"));
            options.setAppActivity(props.getProperty("androidTvAppActivity"));
            // Key for TV: Ensure D-Pad interaction capabilities if custom

            driver.set(new AndroidDriver(new URL(props.getProperty("androidTvUrl")), options));

        } else if (platform.equalsIgnoreCase("appletv")) {
            XCUITestOptions options = new XCUITestOptions();
            options.setPlatformName("tvOS"); // Specific to Apple TV
            options.setDeviceName(props.getProperty("appleTvDeviceName"));
            options.setUdid(props.getProperty("appleTvUdid"));
            options.setBundleId(props.getProperty("appleTvBundleId"));

            driver.set(new IOSDriver(new URL(props.getProperty("appleTvUrl")), options));

        } else if (platform.equalsIgnoreCase("tizen")) {
            // Pseudocode for Tizen - requires appium-tizen-driver
            UiAutomator2Options options = new UiAutomator2Options(); // Placeholder, Tizen uses specific options
            options.setPlatformName("Tizen");
            options.setDeviceName(props.getProperty("tizenDeviceName"));
            options.setAppPackage(props.getProperty("tizenAppPackage"));

            driver.set(new AndroidDriver(new URL(props.getProperty("tizenUrl")), options)); // Actually TizenDriver
        } else if (platform.equalsIgnoreCase("roku")) {
            log.info("Roku uses ECP (HTTP). Driver is null. Use RokuHelper.");
            rokuIp = props.getProperty("rokuIpAddress");
            // No Appium Driver for Roku ECP
        } else if (platform.equalsIgnoreCase("chromecast")) {
            log.info("Connecting to Chromecast Debugger via ChromeDriver");
            String chromeDriverPath = props.getProperty("chromedriverPath");
            if (chromeDriverPath != null && !chromeDriverPath.isEmpty()) {
                System.setProperty("webdriver.chrome.driver", chromeDriverPath);
            }

            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("debuggerAddress",
                    props.getProperty("chromecastIp") + ":" + props.getProperty("chromecastPort"));

            driver.set(new ChromeDriver(options));
        }

        if (getDriver() != null) {
            getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        }
    }

    @AfterMethod
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
        }
    }
}
