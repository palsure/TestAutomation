package com.test.automation.utils;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;

import static com.test.automation.utils.CommonUtil.getProperty;
import static com.test.automation.utils.CommonUtil.getValidURL;
import static com.test.automation.utils.CommonUtil.isValidURL;
import static com.test.automation.utils.Constants.BrowserType.CHROME;
import static com.test.automation.utils.Constants.BrowserType.EDGE;
import static com.test.automation.utils.Constants.BrowserType.FIREFOX;
import static com.test.automation.utils.Constants.BrowserType.SAFARI;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Slf4j
public class DriverManager {

    private static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<>();

    public static RemoteWebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(RemoteWebDriver driver) {
        DriverManager.driver.set(driver);
    }

    public void initializeDriver() {
        String browser = getProperty("browser");
        String testLab = getProperty("testLab");
        String labUrl = getProperty("labUrl");
        int implicitWait = Integer.parseInt(getProperty("implicit.wait"));
        int pageLoadWait = Integer.parseInt(getProperty("pageload.wait"));
        int scriptWait = Integer.parseInt(getProperty("script.wait"));
        boolean isLocalExecution = isEmpty(testLab) || testLab.equalsIgnoreCase("local");
        boolean isLabExecution = isNotEmpty(testLab) && isValidURL(labUrl);
        log.info("Browser Name: %s".formatted(browser));
        if (isLocalExecution) {
//            SeleniumManager.getInstance();
            if (browser.equalsIgnoreCase(CHROME.name())) {
                setDriver(new ChromeDriver((ChromeOptions) getDriverOptions(CHROME)));
            } else if (browser.equalsIgnoreCase(FIREFOX.name())) {
                setDriver(new FirefoxDriver((FirefoxOptions) getDriverOptions(FIREFOX)));
            } else if (browser.equalsIgnoreCase(EDGE.name())) {
                setDriver(new EdgeDriver((EdgeOptions) getDriverOptions(EDGE)));
            } else if (browser.equalsIgnoreCase(SAFARI.name())) {
                setDriver(new SafariDriver());
            }
            log.info("Initialized %s driver. %s".formatted(browser, getDriver().getSessionId()));
        } else if (isLabExecution) {
            setDriver(new RemoteWebDriver(Objects.requireNonNull(getValidURL(labUrl)), getDesiredCaps()));
        }
        if (getDriver() == null) {
            throw new WebDriverException("Driver not initialized. Check the browser run config.");
        }
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadWait));
        getDriver().manage().timeouts().scriptTimeout(Duration.ofSeconds(scriptWait));
        getDriver().manage().window().maximize();
    }

    private DesiredCapabilities getDesiredCaps() {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("browser", "Chrome");
        caps.setCapability("browser_version", "91.0");
        caps.setCapability("os", "Windows");
        caps.setCapability("os_version", "10");
        caps.setCapability("name", "DemoQATest");
        caps.setCapability("buildName", "DemoQATestFlightBooking");
        return caps;
    }

    private <T extends AbstractDriverOptions<T>> T getDriverOptions(Constants.BrowserType browser) {
        boolean headless = getProperty("headless").equalsIgnoreCase("true");
        LoggingPreferences logs = new LoggingPreferences();
        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        String logCap = "goog:loggingPrefs";
        T options;
        switch (browser) {
            case CHROME -> {
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("start-maximized");
                if (headless)
                    chromeOptions.addArguments("--headless");
                options = (T) chromeOptions;
                }
            case FIREFOX -> {
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--start-maximized");
                if (headless)
                    firefoxOptions.addArguments("--headless");
                options = (T) firefoxOptions;
                logCap = "moz:firefoxOptions";
            }
            case EDGE -> {
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless)
                    edgeOptions.addArguments("--headless");
                options = (T) edgeOptions;
                logCap = "ms:loggingPrefs";
            }
            case SAFARI -> {
                SafariOptions safariOptions = new SafariOptions();
                options = (T) safariOptions;
            }
            default -> throw new IllegalArgumentException("Invalid Browser: %s".formatted(browser));
        }
        options.setCapability(logCap, logs);
        options.setAcceptInsecureCerts(Boolean.TRUE);
        options.setBrowserVersion("latest");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS);
        return options;
    }

    public void stopDriver() {
        getDriver().quit();
        driver.remove();
    }

    public static void loadUrl() {
        String environment = getProperty("environment");
        String url = getProperty("%s.url".formatted(environment));
        DriverManager.getDriver().get(url);
        log.info("Opened %s url: %s".formatted(environment, url));
    }

    public static void getScreenshot() {
        File screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        try {
            String timestamp = new SimpleDateFormat("MMMdd-HH-mm-ss").format(new Date());
            String fileName = getProperty("screenshot.file").formatted(timestamp);
            File file = new File(fileName);
            file.getParentFile().mkdirs();
            FileHandler.copy(screenshot, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
