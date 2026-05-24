package pageObjects;

import java.time.Duration;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegisterPage {

	WebDriver driver;
	WebDriverWait wait;

	@FindBy(id = "input-firstname")
	WebElement firstNameField;

	@FindBy(id = "input-lastname")
	WebElement lastNameField;

	@FindBy(id = "input-email")
	WebElement emailField;

	@FindBy(id = "input-telephone")
	WebElement telephoneField;

	@FindBy(id = "input-password")
	WebElement passwordField;

	@FindBy(id = "input-confirm")
	WebElement confirmPasswordField;

	@FindBy(id = "input-newsletter")
	WebElement newsletter;

	@FindBy(css = "input[name='agree']")
	WebElement privacyPolicyCheckbox;

	@FindBy(xpath = "//button[normalize-space()='Continue']")
	WebElement continueButton;

	@FindBy(xpath = "//h1[normalize-space()='Your Account Has Been Created!']")
	WebElement successHeading;

	@FindBy(css = "div.alert-danger")
	WebElement warningMessage;

	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new WebDriverWait(driver, Duration.ofSeconds(10));
	}

	// ---------------- INPUT METHODS ----------------

	public void enterFirstName(String firstName) {
		firstNameField.clear();
		firstNameField.sendKeys(firstName);
	}

	public void enterLastName(String lastName) {
		lastNameField.clear();
		lastNameField.sendKeys(lastName);
	}

	public void enterEmail(String email) {
		emailField.clear();
		emailField.sendKeys(email);
	}

	public void enterTelephone(String telephone) {
		telephoneField.clear();
		telephoneField.sendKeys(telephone);
	}

	public void enterPassword(String password) {
		passwordField.clear();
		passwordField.sendKeys(password);
	}

	public void enterConfirmPassword(String confirmPassword) {
		confirmPasswordField.clear();
		confirmPasswordField.sendKeys(confirmPassword);
	}

	// ---------------- FIXED CHECKBOX (NEWSLETTER) ----------------

	public void selectNewsletter(boolean subscribe) {
		wait.until(ExpectedConditions.visibilityOf(newsletter));

		if (newsletter.isSelected() != subscribe) {
			scrollAndClick(newsletter);
		}
	}

	// ---------------- FIXED PRIVACY POLICY (MAIN FIX) ----------------

	public void acceptPrivacyPolicy() {
		wait.until(ExpectedConditions.elementToBeClickable(privacyPolicyCheckbox));
		scrollAndClick(privacyPolicyCheckbox);
	}

	// ---------------- BUTTON ----------------

	public void clickContinue() {
		wait.until(ExpectedConditions.elementToBeClickable(continueButton));
		scrollAndClick(continueButton);
	}

	// ---------------- VALIDATIONS ----------------

	public boolean isRegistrationSuccessful() {
		try {
			return successHeading.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getSuccessHeading() {
		return successHeading.getText();
	}

	public String getWarningMessage() {
		return warningMessage.getText();
	}

	// ---------------- FULL FLOW ----------------

	public void register(String firstName, String lastName, String email, String telephone, String password,
			boolean newsletterFlag) {

		enterFirstName(firstName);
		enterLastName(lastName);
		enterEmail(email);
		enterTelephone(telephone);
		enterPassword(password);
		enterConfirmPassword(password);

		selectNewsletter(newsletterFlag);
		acceptPrivacyPolicy();
		clickContinue();
	}

	// ---------------- SAFE CLICK UTILITY ----------------

	private void scrollAndClick(WebElement element) {
		try {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
			element.click();
		} catch (Exception e) {
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
		}
	}
}