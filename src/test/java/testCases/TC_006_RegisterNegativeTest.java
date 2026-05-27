package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.RegisterPage;

public class TC_006_RegisterNegativeTest extends BaseClass {

	// ── 1. Empty form submission ──────────────────────────────────────────
	@Test(priority = 1, groups = { "regression" })
	public void emptyFormSubmissionTest() {
		logInfo("Testing empty form submission");
		HomePage homePage = new HomePage(driver);
		homePage.navigateToRegister();
		RegisterPage registerPage = new RegisterPage(driver);
		registerPage.clickContinue();

		// Check for page-level alert (privacy policy warning)
		Assert.assertTrue(registerPage.isPageAlertDisplayed(), "Page alert should appear when submitting empty form");

		String warning = registerPage.getPageAlertText();
		Assert.assertTrue(warning.length() > 0, "Validation warning should appear on empty submit");
		logInfo("Empty form validation working: " + warning);
	}

	// ── 2. Invalid email format ───────────────────────────────────────────
	@Test(priority = 2, groups = { "regression" })
	public void invalidEmailFormatTest() {
		logInfo("Testing invalid email format");
		HomePage homePage = new HomePage(driver);
		homePage.navigateToRegister();
		RegisterPage registerPage = new RegisterPage(driver);
		registerPage.enterFirstName("Test");
		registerPage.enterLastName("User");
		registerPage.enterEmail("notanemail"); // Invalid email without @
		registerPage.enterPassword("Test@1234");
		registerPage.selectNewsletter(false);
		registerPage.acceptPrivacyPolicy();
		registerPage.clickContinue();

		// Check for browser's HTML5 validation message
		String validationMessage = registerPage.getEmailFieldValidationMessage();
		Assert.assertNotNull(validationMessage, "Browser validation message should appear");
		Assert.assertTrue(validationMessage.contains("@") || validationMessage.contains("email"),
				"Validation message should indicate invalid email format: " + validationMessage);

		logInfo("Invalid email validation working: " + validationMessage);
	}

	// ── 3. Password too short (boundary) ─────────────────────────────────
	@Test(priority = 3, groups = { "regression" })
	public void shortPasswordTest() {
		logInfo("Testing password below minimum length (boundary)");
		HomePage homePage = new HomePage(driver);
		homePage.navigateToRegister();
		RegisterPage registerPage = new RegisterPage(driver);
		registerPage.enterFirstName("Test");
		registerPage.enterLastName("User");
		registerPage.enterEmail("boundary" + System.currentTimeMillis() + "@gmail.com");

		registerPage.enterPassword("123"); // below min length

		registerPage.selectNewsletter(false);
		registerPage.acceptPrivacyPolicy();
		registerPage.clickContinue();

		// Check for password field error
		Assert.assertTrue(registerPage.isPasswordErrorDisplayed(),
				"Password error should be displayed for short password");

		logInfo("Short password boundary test passed");
	}

	// ── 4. First name exceeds max length (boundary) ───────────────────────
	@Test(priority = 4, groups = { "regression" })
	public void maxLengthFirstNameTest() {
		logInfo("Testing first name exceeding max length (boundary)");
		HomePage homePage = new HomePage(driver);
		homePage.navigateToRegister();
		RegisterPage registerPage = new RegisterPage(driver);
		String longName = "A".repeat(33); // OpenCart limit is 32
		registerPage.enterFirstName(longName);
		registerPage.enterLastName("User");
		registerPage.enterEmail("maxlen" + System.currentTimeMillis() + "@gmail.com");

		registerPage.enterPassword("Test@1234");

		registerPage.selectNewsletter(false);
		registerPage.acceptPrivacyPolicy();
		registerPage.clickContinue();

		// Check for first name field error
		Assert.assertTrue(registerPage.isFirstNameErrorDisplayed(),
				"First name error should be displayed for exceeding max length");

		logInfo("Max length boundary test passed");
	}

	// ── 5. Duplicate email registration ──────────────────────────────────
	@Test(priority = 5, groups = { "regression" })
	public void duplicateEmailTest() {
		logInfo("Testing duplicate email registration");
		HomePage homePage = new HomePage(driver);
		homePage.navigateToRegister();
		RegisterPage registerPage = new RegisterPage(driver);
		// use a known already-registered email from config
		registerPage.enterFirstName("Test");
		registerPage.enterLastName("User");
		registerPage.enterEmail(p.getProperty("username")); // already registered

		registerPage.enterPassword("Test@1234");

		registerPage.selectNewsletter(false);
		registerPage.acceptPrivacyPolicy();
		registerPage.clickContinue();

		// Check for page-level alert for duplicate email
		Assert.assertTrue(registerPage.isPageAlertDisplayed(), "Page alert should be displayed for duplicate email");

		String warning = registerPage.getPageAlertText();
		Assert.assertTrue(warning.toLowerCase().contains("already"),
				"Warning should indicate email is already registered");
		logInfo("Duplicate email test passed");
	}

	// ── 6. Empty first name only (field-level boundary) ───────────────────
	@Test(priority = 6, groups = { "regression" })
	public void emptyFirstNameTest() {
		logInfo("Testing empty first name field");
		HomePage homePage = new HomePage(driver);
		homePage.navigateToRegister();
		RegisterPage registerPage = new RegisterPage(driver);
		registerPage.enterFirstName(""); // empty
		registerPage.enterLastName("User");
		registerPage.enterEmail("empty" + System.currentTimeMillis() + "@gmail.com");

		registerPage.enterPassword("Test@1234");

		registerPage.selectNewsletter(false);
		registerPage.acceptPrivacyPolicy();
		registerPage.clickContinue();

		// Check for first name field error
		Assert.assertTrue(registerPage.isFirstNameErrorDisplayed(),
				"First name error should be displayed when first name is empty");

		logInfo("Empty first name validation passed");
	}

	// ── 7. Empty password test ─────────────────────────────────────────
	@Test(priority = 7, groups = { "regression" })
	public void emptyPasswordTest() {
		logInfo("Testing empty password field");
		HomePage homePage = new HomePage(driver);
		homePage.navigateToRegister();
		RegisterPage registerPage = new RegisterPage(driver);
		registerPage.enterFirstName("Test");
		registerPage.enterLastName("User");
		registerPage.enterEmail("emptypwd" + System.currentTimeMillis() + "@gmail.com");
		registerPage.enterPassword(""); // empty password
		registerPage.selectNewsletter(false);
		registerPage.acceptPrivacyPolicy();
		registerPage.clickContinue();

		// Check for password field error
		Assert.assertTrue(registerPage.isPasswordErrorDisplayed(),
				"Password error should be displayed when password is empty");

		logInfo("Empty password test passed");
	}

	// ── 8. Privacy policy not accepted test ───────────────────────────────
	@Test(priority = 8, groups = { "regression" })
	public void privacyPolicyNotAcceptedTest() {
		logInfo("Testing submission without accepting privacy policy");
		HomePage homePage = new HomePage(driver);
		homePage.navigateToRegister();
		RegisterPage registerPage = new RegisterPage(driver);
		registerPage.enterFirstName("Test");
		registerPage.enterLastName("User");
		registerPage.enterEmail("noprivacy" + System.currentTimeMillis() + "@gmail.com");

		registerPage.enterPassword("Test@1234");

		registerPage.selectNewsletter(false);
		// NOT accepting privacy policy
		registerPage.clickContinue();

		// Check for page-level alert for privacy policy
		Assert.assertTrue(registerPage.isPageAlertDisplayed(),
				"Page alert should be displayed when privacy policy not accepted");

		String warning = registerPage.getPageAlertText();
		Assert.assertTrue(warning.toLowerCase().contains("privacy"), "Warning should mention privacy policy");
		logInfo("Privacy policy validation test passed");
	}
}