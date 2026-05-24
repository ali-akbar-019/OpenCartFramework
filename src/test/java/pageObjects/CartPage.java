package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {

	WebDriver driver;
	WebDriverWait wait;

	public CartPage(WebDriver driver) {

		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		PageFactory.initElements(driver, this);
	}

	// ===== LOCATORS =====

	By cartHeading = By.xpath("//div[@id='shopping-cart']//h1");

	By productName = By.xpath("//td[@class='text-start text-wrap']//a[contains(text(),'iMac')]");

	By removeButton = By.xpath("//a[@aria-label='Remove']");

	By emptyCartMessage = By.xpath("//p[contains(text(),'Your shopping cart is empty')]");

	// ===== METHODS =====

	public boolean isCartPageDisplayed() {

		try {

			WebElement heading = wait.until(ExpectedConditions.visibilityOfElementLocated(cartHeading));

			return heading.isDisplayed();

		} catch (Exception e) {

			return false;
		}
	}

	public boolean isProductDisplayed() {

		try {

			WebElement product = wait.until(ExpectedConditions.visibilityOfElementLocated(productName));

			return product.isDisplayed();

		} catch (Exception e) {

			return false;
		}
	}

	public void removeFromCart() {

		WebElement removeBtn = wait.until(ExpectedConditions.elementToBeClickable(removeButton));

		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", removeBtn);

		((JavascriptExecutor) driver).executeScript("arguments[0].click();", removeBtn);

		// wait until cart updates
		wait.until(ExpectedConditions.or(ExpectedConditions.visibilityOfElementLocated(emptyCartMessage),
				ExpectedConditions.invisibilityOfElementLocated(productName)));
	}

	public boolean isCartEmpty() {

		try {

			WebElement emptyMsg = wait.until(ExpectedConditions.visibilityOfElementLocated(emptyCartMessage));

			return emptyMsg.isDisplayed();

		} catch (Exception e) {

			return false;
		}
	}
}