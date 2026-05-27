package pageObjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage {
	WebDriver driver;
	WebDriverWait wait;

	By checkoutBtn = By.xpath("//a[contains(text(),'Checkout')]");
	By cartTotal = By.xpath("//tfoot//tr[last()]//td[last()]");
	By qtyInput = By.xpath("//div[@id='shopping-cart']//input[@type='text']");
	By updateBtn = By.xpath("//button[@title='Update']");

	public CheckoutPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		PageFactory.initElements(driver, this);
	}

	public void clickCheckout() {
		WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(checkoutBtn));
		((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
	}

	public String getCartTotal() {
		WebElement total = wait.until(ExpectedConditions.visibilityOfElementLocated(cartTotal));
		return total.getText();
	}

	public void updateQuantity(String qty) {
		WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(qtyInput));
		input.clear();
		input.sendKeys(qty);
		WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(updateBtn));
		btn.click();
	}

	public String getQuantityValue() {
		WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(qtyInput));
		return input.getAttribute("value");
	}

	public boolean isCheckoutPage() {
		return driver.getCurrentUrl().contains("checkout");
	}
}