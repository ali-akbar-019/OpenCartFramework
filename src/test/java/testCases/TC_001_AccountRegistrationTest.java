package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.RegisterPage;
import utilities.XLUtility;

public class TC_001_AccountRegistrationTest extends BaseClass {

	@Test(groups = { "regression", "smoke" })
	public void accountRegistrationTest() {

		logInfo("========================================");
		logInfo("TEST STARTED: accountRegistrationTest");
		logInfo("========================================");

		// step 1 — navigate to register page
		logInfo("STEP 1: Navigating to Register page from Home page");
		HomePage homePage = new HomePage(driver);
		homePage.navigateToRegister();
		logInfo("✓ Navigated to Register page");

		// step 2 — fill registration form
		logInfo("STEP 2: Filling registration form with user details");
		RegisterPage registerPage = new RegisterPage(driver);

		String email = "test" + System.currentTimeMillis() + "@gmail.com";
		logInfo("Generated unique email: " + email);

		logInfo("Entering First Name: Test");
		registerPage.enterFirstName("Test");

		logInfo("Entering Last Name: User");
		registerPage.enterLastName("User");

		logInfo("Entering Email: " + email);
		registerPage.enterEmail(email);

		logInfo("Entering Password: Test@1234");
		registerPage.enterPassword("Test@1234");

		logInfo("Selecting newsletter subscription: Yes");
		registerPage.selectNewsletter(true);

		logInfo("Accepting privacy policy");
		registerPage.acceptPrivacyPolicy();

		logInfo("Clicking Continue button");
		registerPage.clickContinue();
		logInfo("✓ Registration form submitted");

		// step 3 — verify registration success
		logInfo("STEP 3: Verifying registration success message");
		boolean isSuccess = registerPage.isRegistrationSuccessful();
		Assert.assertTrue(isSuccess, "Registration success heading should be displayed");
		logInfo("✓ Registration successful! Success message displayed");

		// step 4 — save data to Excel
		logInfo("STEP 4: Saving registration data to Excel file");
		try {
			String path = System.getProperty("user.dir") + "\\testData\\TestData.xlsx";
			XLUtility xl = new XLUtility(path);
			logInfo("Excel file path: " + path);

			logInfo("Writing to RegisterData sheet...");
			xl.setCellData("RegisterData", 1, 0, "Test"); // FirstName
			xl.setCellData("RegisterData", 1, 1, "User"); // LastName
			xl.setCellData("RegisterData", 1, 2, email); // Email
			xl.setCellData("RegisterData", 1, 3, "Test@1234"); // Password
			xl.setCellData("RegisterData", 1, 4, "Yes"); // Newsletter
			xl.setCellData("RegisterData", 1, 5, "Pass"); // Expected
			logInfo("✓ RegisterData sheet updated");

			logInfo("Writing to LoginData sheet for auto-sync...");
			xl.setCellData("LoginData", 1, 0, "validLogin");
			xl.setCellData("LoginData", 1, 1, email);
			xl.setCellData("LoginData", 1, 2, "Test@1234");
			xl.setCellData("LoginData", 1, 3, "Pass");
			logInfo("✓ LoginData sheet updated with registered email");

			logInfo("Registration data saved successfully in Excel");

		} catch (Exception e) {
			logInfo("ERROR: Failed to save data to Excel: " + e.getMessage());
			e.printStackTrace();
		}

		logInfo("========================================");
		logInfo("TEST PASSED: accountRegistrationTest");
		logInfo("New user registered with email: " + email);
		logInfo("========================================");
	}
}