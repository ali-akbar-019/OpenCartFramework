package testCases;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.Status;

import utilities.ExtentReportManager;

public class BaseClass {

	public static WebDriver driver;
	public Properties p;

	@BeforeMethod(groups = { "sanity", "smoke", "regression", "master" })
	@Parameters({ "browser", "runMode" })
	public void setup(String browser, String runMode) throws IOException {

		FileReader file = new FileReader(System.getProperty("user.dir") + "\\src\\test\\resources\\config.properties");

		p = new Properties();
		p.load(file);

		try {

			if (runMode.equalsIgnoreCase("grid")) {

				URL gridUrl = new URL("http://169.254.168.210:4444");

				switch (browser.toLowerCase()) {

				case "chrome":
					ChromeOptions chromeOptions = new ChromeOptions();
					driver = new RemoteWebDriver(gridUrl, chromeOptions);
					break;

				case "firefox":
					FirefoxOptions firefoxOptions = new FirefoxOptions();
					driver = new RemoteWebDriver(gridUrl, firefoxOptions);
					break;

				case "edge":
					EdgeOptions edgeOptions = new EdgeOptions();
					driver = new RemoteWebDriver(gridUrl, edgeOptions);
					break;

				default:
					ChromeOptions defaultOptions = new ChromeOptions();
					driver = new RemoteWebDriver(gridUrl, defaultOptions);
					break;
				}

			} else {

				switch (browser.toLowerCase()) {

				case "chrome":
					driver = new ChromeDriver();
					break;

				case "edge":
					driver = new EdgeDriver();
					break;

				case "firefox":
					driver = new FirefoxDriver();
					break;

				default:
					driver = new ChromeDriver();
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get(p.getProperty("appURL"));
	}

	@AfterMethod(groups = { "sanity", "smoke", "regression", "master" })
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	public String captureScreen(String testName) throws IOException {

		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);

		String path = System.getProperty("user.dir") + "\\screenshots\\" + testName + "_" + timeStamp + ".png";

		File target = new File(path);
		FileHandler.copy(source, target);

		return path;
	}

	public void logInfo(String message) {
		ExtentReportManager.getTest().log(Status.INFO, message);
	}
}