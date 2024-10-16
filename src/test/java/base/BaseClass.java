package base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.asserts.SoftAssert;

import io.qameta.allure.Allure;
import utils.ReadConfig;
import webelements.HomeElements;

public class BaseClass {

	public static WebDriver driver;

	ReadConfig readConfig = new ReadConfig();
	// Store only URLs, not WebElement references
    public static List<String> links = new ArrayList<>();
    public static List<String> imageSrc = new ArrayList<>();

	@BeforeSuite
	public void startBrowser() {
		openBrowser(readConfig.getBrowser());
		driver.manage().window().maximize();
		driver.get(readConfig.getbaseUrl());
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(15));
		List<WebElement> linkElements = driver.findElements(By.tagName("a"));
        for (WebElement linkElement : linkElements) {
            String href = linkElement.getAttribute("href");
            if (href != null && !href.isEmpty()) {
                links.add(href);
            }
        }
        List<WebElement> imageElements = driver.findElements(By.tagName("img"));
        for(WebElement imgSrc:imageElements) {
        	String src = imgSrc.getAttribute("src");
        	if(src!=null && !src.isEmpty()) {
        		imageSrc.add(src);
        	}
        }
    }

	
//	@BeforeMethod
//	public void closeGoogleAds() {
//		HomeElements homePage = PageFactory.initElements(driver, HomeElements.class);
//		homePage.closeGoogleAds();
//		
//	}

	@AfterSuite
	public void endBrowser() {
		//driver.close();
	}

	public WebDriver openBrowser(String browser) {
		switch (browser.toLowerCase()) {
		case "chrome":
			driver = new ChromeDriver();
			break;
		case "firefox":
			driver = new FirefoxDriver();
			break;
		case "edge":
			driver = new EdgeDriver();
			break;
		default:
			throw new WebDriverException("Invalid Browser: " + browser);
		}
		return driver;
	}

	public static void getScreenshots() {

		String timestamp = new SimpleDateFormat("MM_dd__hh_mm_ss").format(new Date());
		TakesScreenshot sourcefile = ((TakesScreenshot) driver);
		File srcFile = sourcefile.getScreenshotAs(OutputType.FILE);
		File destFile = new File(".//Screenshots//" + timestamp + ".png");
		try {
			FileHandler.copy(srcFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			Allure.addAttachment("Screenshott",
					new FileInputStream(System.getProperty("user.dir") + "\\Screenshots\\" + timestamp + ".png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public Actions actions() {

		Actions actions = new Actions(driver);
		return actions;

	}

	public WebDriverWait explictWait(Duration duration) {

		WebDriverWait explictWait = new WebDriverWait(driver, duration);
		return explictWait;
	}

	public FluentWait<WebDriver> fluentWait(Duration duration, Duration pollingTime) {
		// Define FluentWait with WebDriver as the generic type
		FluentWait<WebDriver> fluentWait = new FluentWait<>(driver);
		fluentWait.withTimeout(duration).pollingEvery(pollingTime);
		return fluentWait;
	}

	
}
