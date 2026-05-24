package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	WebDriver driver;

	@FindBy(xpath = "//input[@id='input-email']")
	WebElement emailField;

	@FindBy(xpath = "//input[@id='input-password']")
	WebElement passwordField;

	@FindBy(xpath = "//button[normalize-space()='Login']")
	WebElement loginButton;

	@FindBy(xpath = "//div[@class='alert alert-danger alert-dismissible']")
	WebElement warningMessage;

	@FindBy(linkText = "Forgotten Password")
	WebElement forgotPasswordLink;

	@FindBy(linkText = "Continue")
	WebElement continueButton; // for registration redirect

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void enterEmail(String email) {
		emailField.clear();
		emailField.sendKeys(email);
	}

	public void enterPassword(String password) {
		passwordField.clear();
		passwordField.sendKeys(password);
	}

	public void clickLogin() {
		loginButton.click();
	}

	// returns warning message text for assertion
	public String getWarningMessage() {
		return warningMessage.getText();
	}

	public boolean isWarningDisplayed() {
		return warningMessage.isDisplayed();
	}

	// combined login action
	public MyAccountPage loginAs(String email, String password) {
		enterEmail(email);
		enterPassword(password);
		clickLogin();
		return new MyAccountPage(driver);
	}

	public void clickForgotPassword() {
		forgotPasswordLink.click();
	}
}