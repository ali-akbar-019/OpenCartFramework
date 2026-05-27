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

		logInfo("Starting login test: " + testCase);

		logInfo("Navigating to Login page");
		HomePage homePage = new HomePage(driver);
		homePage.navigateToLogin();

		logInfo("Entering credentials");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.enterEmail(email);
		loginPage.enterPassword(password);

		logInfo("Clicking Login button");
		loginPage.clickLogin();

		boolean result;

		// VALID LOGIN CASE
		if (expected.equalsIgnoreCase("pass")) {

			logInfo("Valid login validation started");

			MyAccountPage myAccountPage = new MyAccountPage(driver);
			result = myAccountPage.isMyAccountPageDisplayed();

			Assert.assertTrue(result, "My Account page should be displayed");

			// logout
			myAccountPage.clickLogout();
			logInfo("Login SUCCESS for: " + testCase);

		}
		// INVALID LOGIN CASE
		else {

			logInfo("Invalid login validation started");

			// Check for HTML5 validation error first (missing @ symbol, etc.)
			if (loginPage.isEmailValidationErrorDisplayed()) {
				String validationMsg = loginPage.getEmailValidationMessage();
				logInfo("HTML5 validation error detected: " + validationMsg);
				Assert.assertTrue(true, "HTML5 validation prevented form submission");
			}
			// Then check for server-side warning message
			else {
				result = loginPage.isWarningDisplayed();
				Assert.assertTrue(result, "Warning message should be displayed for invalid credentials");
				logInfo("Login WARNING displayed for: " + testCase);
			}
		}

		logInfo("Login test completed: " + testCase);
	}
}