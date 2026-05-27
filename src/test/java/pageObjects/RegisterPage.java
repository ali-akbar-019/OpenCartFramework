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

	// page-level alert (privacy policy, duplicate email)
	@FindBy(xpath = "//div[@class='alert alert-danger alert-dismissible']")
	WebElement pageAlertDanger;

	// field-level errors
	@FindBy(id = "error-firstname")
	WebElement firstNameError;

	@FindBy(id = "error-lastname")
	WebElement lastNameError;

	@FindBy(id = "error-email")
	WebElement emailError;

	@FindBy(id = "error-password")
	WebElement passwordError;

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

	// ---------------- CHECKBOX ----------------

	public void selectNewsletter(boolean subscribe) {
		wait.until(ExpectedConditions.visibilityOf(newsletter));
		if (newsletter.isSelected() != subscribe) {
			scrollAndClick(newsletter);
		}
	}

	// ---------------- PRIVACY POLICY ----------------

	public void acceptPrivacyPolicy() {
		wait.until(ExpectedConditions.elementToBeClickable(privacyPolicyCheckbox));
		scrollAndClick(privacyPolicyCheckbox);
	}

	// ---------------- BUTTON ----------------

	public void clickContinue() {
		wait.until(ExpectedConditions.elementToBeClickable(continueButton));
		scrollAndClick(continueButton);
	}

	// ---------------- SUCCESS VALIDATION ----------------

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

	// ---------------- PAGE ALERT (privacy policy, duplicate email)
	// ----------------

	public boolean isPageAlertDisplayed() {
		try {
			return pageAlertDanger.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public String getPageAlertText() {
		try {
			return pageAlertDanger.getText();
		} catch (Exception e) {
			return "";
		}
	}

	// ---------------- FIELD ERRORS ----------------

	public String getEmailFieldValidationMessage() {
		try {
			wait.until(ExpectedConditions.visibilityOf(emailField));
			return (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].validationMessage;",
					emailField);
		} catch (Exception e) {
			return "";
		}
	}

	public boolean isEmailFieldValid() {
		try {
			wait.until(ExpectedConditions.visibilityOf(emailField));
			Boolean isValid = (Boolean) ((JavascriptExecutor) driver)
					.executeScript("return arguments[0].checkValidity();", emailField);
			return isValid;
		} catch (Exception e) {
			return true;
		}
	}

// Alternative: Check if HTML5 validation is triggered
	public boolean isHtml5ValidationTriggered() {
		try {
			// Check if any form field has validation message
			Boolean hasValidation = (Boolean) ((JavascriptExecutor) driver)
					.executeScript("return document.querySelector(':invalid') !== null;");
			return hasValidation;
		} catch (Exception e) {
			return false;
		}
	}

// Or check for the browser's tooltip/popup message (more complex)
	public boolean isBrowserShowingValidationError() {
		try {
			// Check if email field is marked as invalid by browser
			String invalidClass = (String) ((JavascriptExecutor) driver)
					.executeScript("return arguments[0].getAttribute('aria-invalid');", emailField);
			return "true".equals(invalidClass);
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isFirstNameErrorDisplayed() {
		try {
			wait.until(ExpectedConditions.visibilityOf(firstNameError));
			return firstNameError.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isLastNameErrorDisplayed() {
		try {
			wait.until(ExpectedConditions.visibilityOf(lastNameError));
			return lastNameError.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isEmailErrorDisplayed() {
		try {
			wait.until(ExpectedConditions.visibilityOf(emailError));
			return emailError.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean isPasswordErrorDisplayed() {
		try {
			wait.until(ExpectedConditions.visibilityOf(passwordError));
			return passwordError.isDisplayed();
		} catch (Exception e) {
			return false;
		}
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