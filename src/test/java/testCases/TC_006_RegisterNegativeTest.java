package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.RegisterPage;

public class TC_006_RegisterNegativeTest extends BaseClass {

	// ── 1. Empty form submission ──────────────────────────────────────────
	@Test(priority = 1, groups = { "regression" })
	public void emptyFormSubmissionTest() {
		logInfo("========================================");
		logInfo("TEST STARTED: emptyFormSubmissionTest");
		logInfo("========================================");

		logInfo("STEP 1: Navigating to Register page");
		HomePage homePage = new HomePage(driver);
		homePage.navigateToRegister();
		logInfo("✓ Navigated to Register page");

		logInfo("STEP 2: Submitting empty form without filling any data");
		RegisterPage registerPage = new RegisterPage(driver);
		registerPage.clickContinue();
		logInfo("✓ Continue button clicked without data");

		logInfo("STEP 3: Verifying page alert appears");
		Assert.assertTrue(registerPage.isPageAlertDisplayed(), "Page alert should appear when submitting empty form");
		logInfo("✓ Page alert displayed");

		String warning = registerPage.getPageAlertText();
		logInfo("STEP 4: Verifying warning message is not empty");
		Assert.assertTrue(warning.length() > 0, "Validation warning should appear on empty submit");
		logInfo("Warning message: " + warning);

		logInfo("========================================");
		logInfo("TEST PASSED: emptyFormSubmissionTest");
		logInfo("========================================");
	}

	// ── 2. Invalid email format ───────────────────────────────────────────
	@Test(priority = 2, groups = { "regression" })
	public void invalidEmailFormatTest() {
		logInfo("========================================");
		logInfo("TEST STARTED: invalidEmailFormatTest");
		logInfo("========================================");

		logInfo("STEP 1: Navigating to Register page");
		HomePage homePage = new HomePage(driver);
		homePage.navigateToRegister();
		logInfo("✓ Navigated to Register page");

		logInfo("STEP 2: Filling form with invalid email 'notanemail'");
		RegisterPage registerPage = new RegisterPage(driver);
		registerPage.enterFirstName("Test");
		registerPage.enterLastName("User");
		registerPage.enterEmail("notanemail"); // Invalid email without @
		registerPage.enterPassword("Test@1234");
		registerPage.selectNewsletter(false);
		registerPage.acceptPrivacyPolicy();
		logInfo("✓ Form filled (invalid email: notanemail)");

		logInfo("STEP 3: Submitting form");
		registerPage.clickContinue();
		logInfo("✓ Form submitted");

		logInfo("STEP 4: Verifying HTML5 validation message");
		String validationMessage = registerPage.getEmailFieldValidationMessage();
		Assert.assertNotNull(validationMessage, "Browser validation message should appear");
		Assert.assertTrue(validationMessage.contains("@") || validationMessage.contains("email"),
				"Validation message should indicate invalid email format: " + validationMessage);
		logInfo("Validation message: " + validationMessage);

		logInfo("========================================");
		logInfo("TEST PASSED: invalidEmailFormatTest");
		logInfo("========================================");
	}

	// ── 3. Password too short (boundary) ─────────────────────────────────
	@Test(priority = 3, groups = { "regression" })
	public void shortPasswordTest() {
		logInfo("========================================");
		logInfo("TEST STARTED: shortPasswordTest (Boundary)");
		logInfo("========================================");

		logInfo("STEP 1: Navigating to Register page");
		HomePage homePage = new HomePage(driver);
		homePage.navigateToRegister();
		logInfo("✓ Navigated to Register page");

		logInfo("STEP 2: Filling form with short password '123' (below min length)");
		RegisterPage registerPage = new RegisterPage(driver);
		registerPage.enterFirstName("Test");
		registerPage.enterLastName("User");
		registerPage.enterEmail("boundary" + System.currentTimeMillis() + "@gmail.com");
		registerPage.enterPassword("123"); // below min length
		registerPage.selectNewsletter(false);
		registerPage.acceptPrivacyPolicy();
		logInfo("✓ Form filled with password length: 3 characters (min is 4)");

		logInfo("STEP 3: Submitting form");
		registerPage.clickContinue();
		logInfo("✓ Form submitted");

		logInfo("STEP 4: Verifying password error message");
		Assert.assertTrue(registerPage.isPasswordErrorDisplayed(),
				"Password error should be displayed for short password");
		logInfo("✓ Password error displayed for short password");

		logInfo("========================================");
		logInfo("TEST PASSED: shortPasswordTest");
		logInfo("========================================");
	}

	// ── 4. First name exceeds max length (boundary) ───────────────────────
	@Test(priority = 4, groups = { "regression" })
	public void maxLengthFirstNameTest() {
		logInfo("========================================");
		logInfo("TEST STARTED: maxLengthFirstNameTest (Boundary)");
		logInfo("========================================");

		logInfo("STEP 1: Navigating to Register page");
		HomePage homePage = new HomePage(driver);
		homePage.navigateToRegister();
		logInfo("✓ Navigated to Register page");

		String longName = "A".repeat(33); // OpenCart limit is 32
		logInfo("STEP 2: Filling form with first name of 33 characters (max is 32)");
		RegisterPage registerPage = new RegisterPage(driver);
		registerPage.enterFirstName(longName);
		registerPage.enterLastName("User");
		registerPage.enterEmail("maxlen" + System.currentTimeMillis() + "@gmail.com");
		registerPage.enterPassword("Test@1234");
		registerPage.selectNewsletter(false);
		registerPage.acceptPrivacyPolicy();
		logInfo("✓ Form filled with first name length: 33 characters");

		logInfo("STEP 3: Submitting form");
		registerPage.clickContinue();
		logInfo("✓ Form submitted");

		logInfo("STEP 4: Verifying first name error message");
		Assert.assertTrue(registerPage.isFirstNameErrorDisplayed(),
				"First name error should be displayed for exceeding max length");
		logInfo("✓ First name error displayed for exceeding max length");

		logInfo("========================================");
		logInfo("TEST PASSED: maxLengthFirstNameTest");
		logInfo("========================================");
	}

	// ── 5. Duplicate email registration ──────────────────────────────────
	@Test(priority = 5, groups = { "regression" })
	public void duplicateEmailTest() {
		logInfo("========================================");
		logInfo("TEST STARTED: duplicateEmailTest");
		logInfo("========================================");

		logInfo("STEP 1: Navigating to Register page");
		HomePage homePage = new HomePage(driver);
		homePage.navigateToRegister();
		logInfo("✓ Navigated to Register page");

		String existingEmail = p.getProperty("username");
		logInfo("STEP 2: Filling form with existing email: " + existingEmail);
		RegisterPage registerPage = new RegisterPage(driver);
		registerPage.enterFirstName("Test");
		registerPage.enterLastName("User");
		registerPage.enterEmail(existingEmail); // already registered
		registerPage.enterPassword("Test@1234");
		registerPage.selectNewsletter(false);
		registerPage.acceptPrivacyPolicy();
		logInfo("✓ Form filled with duplicate email");

		logInfo("STEP 3: Submitting form");
		registerPage.clickContinue();
		logInfo("✓ Form submitted");

		logInfo("STEP 4: Verifying duplicate email alert");
		Assert.assertTrue(registerPage.isPageAlertDisplayed(), "Page alert should be displayed for duplicate email");
		logInfo("✓ Page alert displayed");

		String warning = registerPage.getPageAlertText();
		Assert.assertTrue(warning.toLowerCase().contains("already"),
				"Warning should indicate email is already registered");
		logInfo("Warning message: " + warning);

		logInfo("========================================");
		logInfo("TEST PASSED: duplicateEmailTest");
		logInfo("========================================");
	}

	// ── 6. Empty first name only (field-level boundary) ───────────────────
	@Test(priority = 6, groups = { "regression" })
	public void emptyFirstNameTest() {
		logInfo("========================================");
		logInfo("TEST STARTED: emptyFirstNameTest");
		logInfo("========================================");

		logInfo("STEP 1: Navigating to Register page");
		HomePage homePage = new HomePage(driver);
		homePage.navigateToRegister();
		logInfo("✓ Navigated to Register page");

		logInfo("STEP 2: Filling form with empty first name");
		RegisterPage registerPage = new RegisterPage(driver);
		registerPage.enterFirstName(""); // empty
		registerPage.enterLastName("User");
		registerPage.enterEmail("empty" + System.currentTimeMillis() + "@gmail.com");
		registerPage.enterPassword("Test@1234");
		registerPage.selectNewsletter(false);
		registerPage.acceptPrivacyPolicy();
		logInfo("✓ Form filled (first name empty)");

		logInfo("STEP 3: Submitting form");
		registerPage.clickContinue();
		logInfo("✓ Form submitted");

		logInfo("STEP 4: Verifying first name error message");
		Assert.assertTrue(registerPage.isFirstNameErrorDisplayed(),
				"First name error should be displayed when first name is empty");
		logInfo("✓ First name error displayed");

		logInfo("========================================");
		logInfo("TEST PASSED: emptyFirstNameTest");
		logInfo("========================================");
	}

	// ── 7. Empty password test ─────────────────────────────────────────
	@Test(priority = 7, groups = { "regression" })
	public void emptyPasswordTest() {
		logInfo("========================================");
		logInfo("TEST STARTED: emptyPasswordTest");
		logInfo("========================================");

		logInfo("STEP 1: Navigating to Register page");
		HomePage homePage = new HomePage(driver);
		homePage.navigateToRegister();
		logInfo("✓ Navigated to Register page");

		logInfo("STEP 2: Filling form with empty password");
		RegisterPage registerPage = new RegisterPage(driver);
		registerPage.enterFirstName("Test");
		registerPage.enterLastName("User");
		registerPage.enterEmail("emptypwd" + System.currentTimeMillis() + "@gmail.com");
		registerPage.enterPassword(""); // empty password
		registerPage.selectNewsletter(false);
		registerPage.acceptPrivacyPolicy();
		logInfo("✓ Form filled (password empty)");

		logInfo("STEP 3: Submitting form");
		registerPage.clickContinue();
		logInfo("✓ Form submitted");

		logInfo("STEP 4: Verifying password error message");
		Assert.assertTrue(registerPage.isPasswordErrorDisplayed(),
				"Password error should be displayed when password is empty");
		logInfo("✓ Password error displayed");

		logInfo("========================================");
		logInfo("TEST PASSED: emptyPasswordTest");
		logInfo("========================================");
	}

	// ── 8. Privacy policy not accepted test ───────────────────────────────
	@Test(priority = 8, groups = { "regression" })
	public void privacyPolicyNotAcceptedTest() {
		logInfo("========================================");
		logInfo("TEST STARTED: privacyPolicyNotAcceptedTest");
		logInfo("========================================");

		logInfo("STEP 1: Navigating to Register page");
		HomePage homePage = new HomePage(driver);
		homePage.navigateToRegister();
		logInfo("✓ Navigated to Register page");

		logInfo("STEP 2: Filling form with all valid data");
		RegisterPage registerPage = new RegisterPage(driver);
		registerPage.enterFirstName("Test");
		registerPage.enterLastName("User");
		registerPage.enterEmail("noprivacy" + System.currentTimeMillis() + "@gmail.com");
		registerPage.enterPassword("Test@1234");
		registerPage.selectNewsletter(false);
		// NOT accepting privacy policy
		logInfo("✓ Form filled (privacy policy NOT accepted)");

		logInfo("STEP 3: Submitting form without accepting privacy policy");
		registerPage.clickContinue();
		logInfo("✓ Form submitted");

		logInfo("STEP 4: Verifying privacy policy alert");
		Assert.assertTrue(registerPage.isPageAlertDisplayed(),
				"Page alert should be displayed when privacy policy not accepted");
		logInfo("✓ Page alert displayed");

		String warning = registerPage.getPageAlertText();
		Assert.assertTrue(warning.toLowerCase().contains("privacy"), "Warning should mention privacy policy");
		logInfo("Warning message: " + warning);

		logInfo("========================================");
		logInfo("TEST PASSED: privacyPolicyNotAcceptedTest");
		logInfo("========================================");
	}
}