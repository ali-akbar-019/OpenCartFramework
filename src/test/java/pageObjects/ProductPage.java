package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {

	WebDriver driver;
	WebDriverWait wait;

	public ProductPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "button-cart")
	WebElement addToCartButton;

	By cartButton = By.xpath("//div[@id='cart']");

	@FindBy(css = ".alert-success")
	WebElement successMessage;

	@FindBy(xpath = "//button[@aria-label='Remove']")
	WebElement removeFromCartButton;

	@FindBy(xpath = "//a[@title='Shopping Cart']")
	WebElement viewCartLink;

	// product name
	By productHeading = By.xpath("//h1");
	By productPrice = By.xpath("//ul[@class='list-unstyled']//li//h2");
	By productImage = By.xpath("//img[@class='img-fluid']");
	By quantityInput = By.id("input-quantity");
	By reviewsTab = By.xpath("//a[contains(text(),'Reviews')]");
	By dangerAlert = By.cssSelector(".alert-danger");

	public String getProductName() {
		WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(productHeading));
		return el.getText();
	}

	public String getProductPrice() {
		WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(productPrice));
		return el.getText();
	}

	public boolean isProductImageDisplayed() {
		WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(productImage));
		return el.isDisplayed() && el.getAttribute("src") != null;
	}

	public boolean isAddToCartButtonDisplayed() {
		return addToCartButton.isDisplayed() && addToCartButton.isEnabled();
	}

	public String getDefaultQuantity() {
		WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(quantityInput));
		return el.getAttribute("value");
	}

	public void setQuantity(String qty) {
		WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(quantityInput));
		el.clear();
		el.sendKeys(qty);
	}

	public boolean isDangerAlertDisplayed() {
		try {
			WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(dangerAlert));
			return el.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isReviewTabDisplayed() {
		WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(reviewsTab));
		return el.isDisplayed();
	}

	public String getCartButtonText() {
		WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(cartButton));
		return el.getText();
	}

	public void addToCart() {

		wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));

		// scroll into view (prevents click interception)
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartButton);

		addToCartButton.click();

		// wait for success message OR cart update
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert-success")));
	}

	public String getSuccessMessage() {
		return successMessage.getText();
	}

	public String getCartText() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(cartButton));

		return element.getText();
	}

	public boolean isItemAddedToCart() {
		return getCartText().toLowerCase().contains("item");
	}

	public void clickViewCart() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		By viewCart = By.xpath("//a[@title='Shopping Cart']");

		WebElement cartLink = wait.until(ExpectedConditions.presenceOfElementLocated(viewCart));

		// scroll properly
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", cartLink);

		// wait a bit for bootstrap animation
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// JS click avoids interception
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", cartLink);
	}

	public void removeFromCart() {
		removeFromCartButton.click();
	}
}