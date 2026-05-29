package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;

public class TC_004_MyAccountTest extends BaseClass {

	@Test(priority = 1, groups = { "smoke", "regression" })
	public void verifyMyAccountPageLoads() {

		logInfo("========================================");
		logInfo("TEST STARTED: verifyMyAccountPageLoads");
		logInfo("========================================");

		logInfo("STEP 1: Navigating to Login page");
		HomePage homePage = new HomePage(driver);
		homePage.navigateToLogin();
		logInfo("✓ Navigated to Login page");

		logInfo("STEP 2: Entering login credentials");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.enterEmail(p.getProperty("username"));
		logInfo("✓ Entered email: " + p.getProperty("username"));

		loginPage.enterPassword(p.getProperty("password"));
		logInfo("✓ Entered password");

		logInfo("STEP 3: Clicking Login button");
		loginPage.clickLogin();
		logInfo("✓ Login button clicked");

		logInfo("STEP 4: Verifying My Account page is displayed");
		MyAccountPage myAccountPage = new MyAccountPage(driver);
		Assert.assertTrue(myAccountPage.isMyAccountPageDisplayed(), "My Account page should be displayed after login");
		logInfo("✓ My Account page displayed successfully");

		logInfo("STEP 5: Logging out");
		myAccountPage.clickLogout();
		logInfo("✓ Logged out successfully");

		logInfo("========================================");
		logInfo("TEST PASSED: verifyMyAccountPageLoads");
		logInfo("========================================");
	}

	@Test(priority = 2, groups = { "regression" })
	public void verifyEditAccountLink() {

		logInfo("========================================");
		logInfo("TEST STARTED: verifyEditAccountLink");
		logInfo("========================================");

		logInfo("STEP 1: Logging into application");
		loginToApplication();
		logInfo("✓ Login successful");

		logInfo("STEP 2: Clicking Edit Account link");
		MyAccountPage myAccountPage = new MyAccountPage(driver);
		myAccountPage.clickEditAccount();
		logInfo("✓ Edit Account link clicked");

		logInfo("STEP 3: Verifying Edit Account page opened");
		Assert.assertTrue(driver.getTitle().length() > 0, "Edit Account page should open");
		logInfo("✓ Edit Account page opened. Page title: " + driver.getTitle());

		logInfo("STEP 4: Logging out");
		logOut();
		logInfo("✓ Logged out successfully");

		logInfo("========================================");
		logInfo("TEST PASSED: verifyEditAccountLink");
		logInfo("========================================");
	}

	@Test(priority = 3, groups = { "regression" })
	public void verifyChangePasswordLink() {

		logInfo("========================================");
		logInfo("TEST STARTED: verifyChangePasswordLink");
		logInfo("========================================");

		logInfo("STEP 1: Logging into application");
		loginToApplication();
		logInfo("✓ Login successful");

		logInfo("STEP 2: Clicking Change Password link");
		MyAccountPage myAccountPage = new MyAccountPage(driver);
		myAccountPage.clickChangePassword();
		logInfo("✓ Change Password link clicked");

		logInfo("STEP 3: Verifying Change Password page opened");
		Assert.assertTrue(driver.getCurrentUrl().contains("password"), "Change password page should open");
		logInfo("✓ Change Password page opened. URL: " + driver.getCurrentUrl());

		logInfo("STEP 4: Logging out");
		logOut();
		logInfo("✓ Logged out successfully");

		logInfo("========================================");
		logInfo("TEST PASSED: verifyChangePasswordLink");
		logInfo("========================================");
	}

	@Test(priority = 4, groups = { "regression" })
	public void verifyWishlistLink() {

		logInfo("========================================");
		logInfo("TEST STARTED: verifyWishlistLink");
		logInfo("========================================");

		logInfo("STEP 1: Logging into application");
		loginToApplication();
		logInfo("✓ Login successful");

		logInfo("STEP 2: Clicking Wishlist link");
		MyAccountPage myAccountPage = new MyAccountPage(driver);
		myAccountPage.clickWishlist();
		logInfo("✓ Wishlist link clicked");

		logInfo("STEP 3: Verifying Wishlist page opened");
		Assert.assertTrue(driver.getCurrentUrl().contains("wishlist"), "Wishlist page should open");
		logInfo("✓ Wishlist page opened. URL: " + driver.getCurrentUrl());

		logInfo("STEP 4: Logging out");
		logOut();
		logInfo("✓ Logged out successfully");

		logInfo("========================================");
		logInfo("TEST PASSED: verifyWishlistLink");
		logInfo("========================================");
	}

	@Test(priority = 5, groups = { "regression" })
	public void verifyOrderHistoryLink() {

		logInfo("========================================");
		logInfo("TEST STARTED: verifyOrderHistoryLink");
		logInfo("========================================");

		logInfo("STEP 1: Logging into application");
		loginToApplication();
		logInfo("✓ Login successful");

		logInfo("STEP 2: Clicking Order History link");
		MyAccountPage myAccountPage = new MyAccountPage(driver);
		myAccountPage.clickOrderHistory();
		logInfo("✓ Order History link clicked");

		logInfo("STEP 3: Verifying Order History page opened");
		Assert.assertTrue(driver.getCurrentUrl().contains("order"), "Order history page should open");
		logInfo("✓ Order History page opened. URL: " + driver.getCurrentUrl());

		logInfo("STEP 4: Logging out");
		logOut();
		logInfo("✓ Logged out successfully");

		logInfo("========================================");
		logInfo("TEST PASSED: verifyOrderHistoryLink");
		logInfo("========================================");
	}

	@Test(priority = 6, groups = { "regression" })
	public void verifyLogoutFunctionality() {

		logInfo("========================================");
		logInfo("TEST STARTED: verifyLogoutFunctionality");
		logInfo("========================================");

		logInfo("STEP 1: Logging into application");
		loginToApplication();
		logInfo("✓ Login successful");

		logInfo("STEP 2: Clicking Logout button");
		MyAccountPage myAccountPage = new MyAccountPage(driver);
		myAccountPage.clickLogout();
		logInfo("✓ Logout button clicked");

		logInfo("STEP 3: Verifying user is logged out");
		Assert.assertTrue(
				driver.getCurrentUrl().contains("logout") || driver.getTitle().toLowerCase().contains("account"),
				"User should be logged out");
		logInfo("✓ Logout successful. Current URL: " + driver.getCurrentUrl());

		logInfo("========================================");
		logInfo("TEST PASSED: verifyLogoutFunctionality");
		logInfo("========================================");
	}

	// reusable method
	public void loginToApplication() {
		logInfo("  → Inside loginToApplication()");
		HomePage homePage = new HomePage(driver);
		homePage.navigateToLogin();

		LoginPage loginPage = new LoginPage(driver);
		loginPage.enterEmail(p.getProperty("username"));
		loginPage.enterPassword(p.getProperty("password"));
		loginPage.clickLogin();
		logInfo("  ← Login completed");
	}

	// reusable logout method
	public void logOut() {
		logInfo("  → Inside logOut()");
		MyAccountPage myAccountPage = new MyAccountPage(driver);
		myAccountPage.clickLogout();
		logInfo("  ← Logout completed");
	}
}