package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.RegisterPage;
import utilities.XLUtility;

public class TC_001_AccountRegistrationTest extends BaseClass {

	@Test(groups = { "regression", "smoke" })
	public void accountRegistrationTest() {

		logInfo("Starting account registration test");

		// step 1 — navigate to register page
		logInfo("Navigating to Register page from Home page");
		HomePage homePage = new HomePage(driver);
		homePage.navigateToRegister();

		// step 2 — fill registration form
		logInfo("Filling registration form with user details");
		RegisterPage registerPage = new RegisterPage(driver);

		String email = "test" + System.currentTimeMillis() + "@gmail.com";

		logInfo("Entering user details (First name, Last name, Email)");
		registerPage.enterFirstName("Test");
		registerPage.enterLastName("User");
		registerPage.enterEmail(email);

		logInfo("Entering password");
		registerPage.enterPassword("Test@1234");

		logInfo("Selecting newsletter subscription");
		registerPage.selectNewsletter(true);

		logInfo("Accepting privacy policy");
		registerPage.acceptPrivacyPolicy();

		logInfo("Clicking Continue button");
		registerPage.clickContinue();

		// step 3 — verify registration success
		logInfo("Verifying registration success message");
		Assert.assertTrue(registerPage.isRegistrationSuccessful(), "Registration success heading should be displayed");

		logInfo("Account registration test completed successfully");
		// SAVE EMAIL TO EXCEL (ADDED ONLY PART)
		try {
			String path = System.getProperty("user.dir") + "\\testData\\TestData.xlsx";
			XLUtility xl = new XLUtility(path);

			xl.setCellData("RegisterData", 1, 0, "Test"); // FirstName
			xl.setCellData("RegisterData", 1, 1, "User"); // LastName
			xl.setCellData("RegisterData", 1, 2, email); // Email
			xl.setCellData("RegisterData", 1, 3, "Test@1234"); // Password
			xl.setCellData("RegisterData", 1, 4, "Yes"); // Newsletter
			xl.setCellData("RegisterData", 1, 5, "Pass"); // Expected
			// if pss then mark the cell as green
			// xl.fillGreenColor("RegisterData", 1, 5);
			// LoginData (AUTO SYNC FROM REGISTRATION)
			xl.setCellData("LoginData", 1, 0, "validLogin");
			xl.setCellData("LoginData", 1, 1, email);
			xl.setCellData("LoginData", 1, 2, "Test@1234");
			xl.setCellData("LoginData", 1, 3, "Pass");
			logInfo("Register data saved successfully in Excel");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}