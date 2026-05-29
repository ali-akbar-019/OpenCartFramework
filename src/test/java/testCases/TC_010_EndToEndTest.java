package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.CartPage;
import pageObjects.CheckoutPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import pageObjects.ProductPage;
import pageObjects.RegisterPage;
import pageObjects.SearchPage;

public class TC_010_EndToEndTest extends BaseClass {

	@Test(priority = 1, groups = { "regression", "e2e" })
	public void completeUserJourneyTest() {

		logInfo("========== STARTING END-TO-END TEST ==========");
		logInfo("User Journey: Register → Login → Search → Add to Cart → Checkout");

		HomePage homePage = new HomePage(driver);
		ProductPage productPage = new ProductPage(driver);

		// Generate unique email for registration
		String uniqueEmail = "e2e_" + System.currentTimeMillis() + "@test.com";

		// ========== STEP 1: REGISTER ==========
		logInfo("STEP 1: Registering new user with email: " + uniqueEmail);
		homePage.navigateToRegister();

		RegisterPage registerPage = new RegisterPage(driver);
		registerPage.enterFirstName("E2E");
		registerPage.enterLastName("User");
		registerPage.enterEmail(uniqueEmail);
		registerPage.enterPassword("Test@1234");
		registerPage.selectNewsletter(true);
		registerPage.acceptPrivacyPolicy();
		registerPage.clickContinue();

		Assert.assertTrue(registerPage.isRegistrationSuccessful(), "Registration should be successful for new user");
		logInfo("✓ Registration successful");

		// ========== STEP 2: LOGOUT ==========
		logInfo("STEP 2: Logging out");
		MyAccountPage myAccountPage = new MyAccountPage(driver);
		myAccountPage.clickLogout();
		logInfo("✓ Logout successful");

		// ========== STEP 3: LOGIN ==========
		logInfo("STEP 3: Logging in with new account");
		homePage.navigateToLogin();
		LoginPage loginPage = new LoginPage(driver);
		loginPage.enterEmail(uniqueEmail);
		loginPage.enterPassword("Test@1234");
		loginPage.clickLogin();

		Assert.assertTrue(myAccountPage.isMyAccountPageDisplayed(),
				"Login should be successful with registered credentials");
		logInfo("✓ Login successful");

		// ========== STEP 4: SEARCH PRODUCT ==========
		logInfo("STEP 4: Searching for product 'Mac'");
		homePage.searchFor("Mac");
		SearchPage searchPage = new SearchPage(driver);

		int resultCount = searchPage.getSearchResultCount();
		Assert.assertTrue(resultCount > 0, "Search should return at least 1 product");
		logInfo("✓ Search returned " + resultCount + " results");

		// ========== STEP 5: ADD TO CART ==========
		logInfo("STEP 5: Adding first product to cart");
		searchPage.selectFirstProduct();

		String beforeCartText = productPage.getCartText();
		productPage.addToCart();

		// Small wait for cart to update
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String afterCartText = productPage.getCartText();
		Assert.assertNotEquals(afterCartText, beforeCartText, "Cart should update after adding product");
		logInfo("✓ Product added to cart. Cart now: " + afterCartText);

		// ========== STEP 6: VIEW CART ==========
		logInfo("STEP 6: Navigating to cart page");
		productPage.clickViewCart();
		CartPage cartPage = new CartPage(driver);

		Assert.assertTrue(cartPage.isCartPageDisplayed(), "Cart page should be displayed");
		Assert.assertTrue(cartPage.isProductDisplayed(), "Product should be visible in cart");
		logInfo("✓ Cart page loaded with product");

		// ========== STEP 7: PROCEED TO CHECKOUT ==========
		logInfo("STEP 7: Proceeding to checkout");
		cartPage.clickCheckout();

		CheckoutPage checkoutPage = new CheckoutPage(driver);
		Assert.assertTrue(checkoutPage.isCheckoutPage(), "Checkout page should be displayed");
		logInfo("✓ Checkout page reached");

		// ========== STEP 8: VERIFY CHECKOUT HAS PRODUCTS ==========
		logInfo("STEP 8: Verifying checkout has products");
		String totalText = checkoutPage.getCartTotal();
		Assert.assertTrue(totalText.contains("$"), "Cart total should display dollar amount");
		logInfo("✓ Checkout cart total: " + totalText);

		logInfo("========== END-TO-END TEST COMPLETED SUCCESSFULLY ==========");
		logInfo("User journey verified: Register → Login → Search → Add to Cart → Checkout");
	}
}