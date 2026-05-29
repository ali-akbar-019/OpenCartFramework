package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import utilities.DataProviders;

public class TC_002_LoginTest extends BaseClass {

	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = { "smoke", "regression" })
	public void loginTest(String testCase, String email, String password, String expected) {

		logInfo("========================================");
		logInfo("TEST STARTED: loginTest - " + testCase);
		logInfo("========================================");

		logInfo("Test Data - Email: " + email + " | Expected: " + expected);
		logInfo("Password: [HIDDEN]");

		logInfo("STEP 1: Navigating to Login page");
		HomePage homePage = new HomePage(driver);
		homePage.navigateToLogin();
		logInfo("✓ Navigated to Login page");

		logInfo("STEP 2: Entering credentials");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.enterEmail(email);
		logInfo("✓ Entered email: " + email);

		loginPage.enterPassword(password);
		logInfo("✓ Entered password");

		logInfo("STEP 3: Clicking Login button");
		loginPage.clickLogin();
		logInfo("✓ Login button clicked");

		boolean result;

		// VALID LOGIN CASE
		if (expected.equalsIgnoreCase("pass")) {

			logInfo("STEP 4: Valid login validation started");
			logInfo("Expected: Login should be successful");

			MyAccountPage myAccountPage = new MyAccountPage(driver);
			result = myAccountPage.isMyAccountPageDisplayed();

			Assert.assertTrue(result, "My Account page should be displayed");
			logInfo("✓ My Account page displayed - Login successful");

			// logout
			logInfo("STEP 5: Logging out after successful login");
			myAccountPage.clickLogout();
			logInfo("✓ Logged out successfully");

			logInfo("RESULT: Login SUCCESS for test case: " + testCase);
			logInfo("========================================");
			logInfo("TEST PASSED: loginTest - " + testCase);
			logInfo("========================================");

		}
		// INVALID LOGIN CASE
		else {

			logInfo("STEP 4: Invalid login validation started");
			logInfo("Expected: Login should fail with appropriate error message");

			// Check for HTML5 validation error first (missing @ symbol, etc.)
			if (loginPage.isEmailValidationErrorDisplayed()) {
				String validationMsg = loginPage.getEmailValidationMessage();
				logInfo("✓ HTML5 validation error detected: " + validationMsg);
				Assert.assertTrue(true, "HTML5 validation prevented form submission");
				logInfo("RESULT: HTML5 validation correctly blocked invalid email format");
			}
			// Then check for server-side warning message
			else {
				result = loginPage.isWarningDisplayed();
				Assert.assertTrue(result, "Warning message should be displayed for invalid credentials");
				logInfo("✓ Server-side warning message displayed for invalid credentials");
				logInfo("RESULT: Login WARNING displayed for test case: " + testCase);
			}

			logInfo("========================================");
			logInfo("TEST PASSED: loginTest - " + testCase + " (Invalid login handled correctly)");
			logInfo("========================================");
		}
	}
}