package pageObjects;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

	WebDriver driver;
	WebDriverWait wait;

	@FindBy(xpath = "//input[@id='input-email']")
	WebElement emailField;

	@FindBy(xpath = "//input[@id='input-password']")
	WebElement passwordField;

	@FindBy(xpath = "//button[normalize-space()='Login']")
	WebElement loginButton;

	@FindBy(xpath = "//div[contains(@class,'alert-danger')]")
	WebElement warningMessage;

	@FindBy(linkText = "Forgotten Password")
	WebElement forgotPasswordLink;

	@FindBy(linkText = "Continue")
	WebElement continueButton; // for registration redirect

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	public void enterEmail(String email) {
		wait.until(ExpectedConditions.visibilityOf(emailField));
		emailField.clear();
		emailField.sendKeys(email);
	}

	public void enterPassword(String password) {
		wait.until(ExpectedConditions.visibilityOf(passwordField));
		passwordField.clear();
		passwordField.sendKeys(password);
	}

	public void clickLogin() {
		wait.until(ExpectedConditions.elementToBeClickable(loginButton));
		scrollAndClick(loginButton);
	}

	// returns warning message text for assertion
	public String getWarningMessage() {
		wait.until(ExpectedConditions.visibilityOf(warningMessage));
		return warningMessage.getText();
	}

	public boolean isWarningDisplayed() {
		try {
			wait.until(ExpectedConditions.visibilityOf(warningMessage));
			return warningMessage.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	// combined login action
	public MyAccountPage loginAs(String email, String password) {
		enterEmail(email);
		enterPassword(password);
		clickLogin();
		return new MyAccountPage(driver);
	}

	public void clickForgotPassword() {
		wait.until(ExpectedConditions.elementToBeClickable(forgotPasswordLink));
		scrollAndClick(forgotPasswordLink);
	}

	// Get HTML5 validation message for email field
	public String getEmailValidationMessage() {
		try {
			wait.until(ExpectedConditions.visibilityOf(emailField));
			return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].validationMessage;",
					emailField);
		} catch (Exception e) {
			return "";
		}
	}

	// Check if email field has HTML5 validation error
	public boolean isEmailValidationErrorDisplayed() {
		try {
			String validationMsg = getEmailValidationMessage();
			return validationMsg != null && !validationMsg.isEmpty();
		} catch (Exception e) {
			return false;
		}
	}

	// ---------------- SAFE CLICK UTILITY ----------------
	private void scrollAndClick(WebElement element) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			wait.until(ExpectedConditions.elementToBeClickable(element));
			element.click();
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		}
	}
}