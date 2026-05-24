package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;

public class TC_004_MyAccountTest extends BaseClass {

	@Test(priority = 1, groups = { "smoke", "regression" })
	public void verifyMyAccountPageLoads() {

		logInfo("Starting My Account page verification test");

		HomePage homePage = new HomePage(driver);
		homePage.navigateToLogin();

		LoginPage loginPage = new LoginPage(driver);
		loginPage.enterEmail(p.getProperty("username"));
		loginPage.enterPassword(p.getProperty("password"));
		loginPage.clickLogin();

		MyAccountPage myAccountPage = new MyAccountPage(driver);

		logInfo("Verifying My Account page is displayed");
		Assert.assertTrue(myAccountPage.isMyAccountPageDisplayed(), "My Account page should be displayed after login");

		logInfo("Logging out");
		myAccountPage.clickLogout();

		logInfo("My Account page loaded successfully");
	}

	@Test(priority = 2, groups = { "regression" })
	public void verifyEditAccountLink() {

		logInfo("Testing Edit Account link");

		loginToApplication();

		MyAccountPage myAccountPage = new MyAccountPage(driver);

		myAccountPage.clickEditAccount();

		Assert.assertTrue(driver.getTitle().length() > 0, "Edit Account page should open");

		logInfo("Edit Account link working");

		logOut();
	}

	@Test(priority = 3, groups = { "regression" })
	public void verifyChangePasswordLink() {

		logInfo("Testing Change Password link");

		loginToApplication();

		MyAccountPage myAccountPage = new MyAccountPage(driver);

		myAccountPage.clickChangePassword();

		Assert.assertTrue(driver.getCurrentUrl().contains("password"), "Change password page should open");

		logInfo("Change password link working");

		logOut();
	}

	@Test(priority = 4, groups = { "regression" })
	public void verifyWishlistLink() {

		logInfo("Testing Wishlist link");

		loginToApplication();

		MyAccountPage myAccountPage = new MyAccountPage(driver);

		myAccountPage.clickWishlist();

		Assert.assertTrue(driver.getCurrentUrl().contains("wishlist"), "Wishlist page should open");

		logInfo("Wishlist link working");

		logOut();
	}

	@Test(priority = 5, groups = { "regression" })
	public void verifyOrderHistoryLink() {

		logInfo("Testing Order History link");

		loginToApplication();

		MyAccountPage myAccountPage = new MyAccountPage(driver);

		myAccountPage.clickOrderHistory();

		Assert.assertTrue(driver.getCurrentUrl().contains("order"), "Order history page should open");

		logInfo("Order history link working");

		logOut();
	}

	@Test(priority = 6, groups = { "regression" })
	public void verifyLogoutFunctionality() {

		logInfo("Testing Logout functionality");

		loginToApplication();

		MyAccountPage myAccountPage = new MyAccountPage(driver);

		myAccountPage.clickLogout();

		Assert.assertTrue(
				driver.getCurrentUrl().contains("logout") || driver.getTitle().toLowerCase().contains("account"),
				"User should be logged out");

		logInfo("Logout working correctly");
	}

	// reusable method
	public void loginToApplication() {
		HomePage homePage = new HomePage(driver);
		homePage.navigateToLogin();

		LoginPage loginPage = new LoginPage(driver);
		loginPage.enterEmail(p.getProperty("username"));
		loginPage.enterPassword(p.getProperty("password"));
		loginPage.clickLogin();
	}

	// reusable logout method
	public void logOut() {
		MyAccountPage myAccountPage = new MyAccountPage(driver);
		myAccountPage.clickLogout();
	}
}