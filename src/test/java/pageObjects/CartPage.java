package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
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

	By cartHeading = By.xpath("//div[@id='shopping-cart']//h1");

	By productName = By.xpath("//td[@class='text-start text-wrap']//a");

	By removeButton = By.xpath("//a[@title='Remove']");
//	By removeButton = By.xpath("//td[@class='text-start text-wrap']//following-sibling::td//button[@data-bs-toggle='tooltip'][@title='Remove']");

	By emptyCartMessage = By.xpath("//div[@id='shopping-cart']//p[contains(text(),'Your shopping cart is empty!')]");

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
		WebElement removeBtn = wait.until(ExpectedConditions.presenceOfElementLocated(removeButton));
		String href = removeBtn.getAttribute("href");
		driver.get(href);
		// navigate to cart page after removal
		driver.get("http://localhost/opencart/index.php?route=checkout/cart&language=en-gb");
		wait.until(ExpectedConditions.or(ExpectedConditions.visibilityOfElementLocated(emptyCartMessage),
				ExpectedConditions.presenceOfElementLocated(By.tagName("body"))));
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