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

		logInfo("========================================");
		logInfo("TEST STARTED: completeUserJourneyTest (E2E)");
		logInfo("========================================");
		logInfo("User Journey: Register → Login → Search → Add to Cart → Checkout");
		logInfo("========================================");

		HomePage homePage = new HomePage(driver);
		ProductPage productPage = new ProductPage(driver);

		// Generate unique email for registration
		String uniqueEmail = "e2e_" + System.currentTimeMillis() + "@test.com";

		// ========== STEP 1: REGISTER ==========
		logInfo("========================================");
		logInfo("STEP 1: REGISTER - Creating new user account");
		logInfo("========================================");

		logInfo("Navigating to Register page");
		homePage.navigateToRegister();
		logInfo("✓ Register page loaded");

		logInfo("Filling registration form");
		RegisterPage registerPage = new RegisterPage(driver);
		registerPage.enterFirstName("E2E");
		registerPage.enterLastName("User");
		registerPage.enterEmail(uniqueEmail);
		logInfo("✓ Email entered: " + uniqueEmail);

		registerPage.enterPassword("Test@1234");
		logInfo("✓ Password entered");

		registerPage.selectNewsletter(true);
		logInfo("✓ Newsletter selected");

		registerPage.acceptPrivacyPolicy();
		logInfo("✓ Privacy policy accepted");

		registerPage.clickContinue();
		logInfo("✓ Registration form submitted");

		Assert.assertTrue(registerPage.isRegistrationSuccessful(), "Registration should be successful for new user");
		logInfo("✓ Registration successful for email: " + uniqueEmail);

		// ========== STEP 2: LOGOUT ==========
		logInfo("========================================");
		logInfo("STEP 2: LOGOUT - Sign out from new account");
		logInfo("========================================");

		MyAccountPage myAccountPage = new MyAccountPage(driver);
		myAccountPage.clickLogout();
		logInfo("✓ Logout successful");

		// ========== STEP 3: LOGIN ==========
		logInfo("========================================");
		logInfo("STEP 3: LOGIN - Sign in with new account");
		logInfo("========================================");

		logInfo("Navigating to Login page");
		homePage.navigateToLogin();
		logInfo("✓ Login page loaded");

		logInfo("Entering credentials");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.enterEmail(uniqueEmail);
		logInfo("✓ Email entered: " + uniqueEmail);

		loginPage.enterPassword("Test@1234");
		logInfo("✓ Password entered");

		loginPage.clickLogin();
		logInfo("✓ Login button clicked");

		Assert.assertTrue(myAccountPage.isMyAccountPageDisplayed(),
				"Login should be successful with registered credentials");
		logInfo("✓ Login successful");

		// ========== STEP 4: SEARCH PRODUCT ==========
		logInfo("========================================");
		logInfo("STEP 4: SEARCH - Find a product");
		logInfo("========================================");

		logInfo("Searching for product: 'Mac'");
		homePage.searchFor("Mac");
		logInfo("✓ Search submitted");

		SearchPage searchPage = new SearchPage(driver);
		int resultCount = searchPage.getSearchResultCount();

		Assert.assertTrue(resultCount > 0, "Search should return at least 1 product");
		logInfo("✓ Search returned " + resultCount + " results");

		// ========== STEP 5: ADD TO CART ==========
		logInfo("========================================");
		logInfo("STEP 5: ADD TO CART - Add product to shopping cart");
		logInfo("========================================");

		logInfo("Selecting first product from search results");
		searchPage.selectFirstProduct();
		logInfo("✓ Product selected");

		logInfo("Getting cart text before adding");
		String beforeCartText = productPage.getCartText();
		logInfo("Cart before add: " + beforeCartText);

		logInfo("Clicking Add to Cart button");
		productPage.addToCart();
		logInfo("✓ Add to Cart clicked");

		logInfo("Waiting for cart to update");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String afterCartText = productPage.getCartText();
		Assert.assertNotEquals(afterCartText, beforeCartText, "Cart should update after adding product");
		logInfo("✓ Product added to cart");
		logInfo("Cart after add: " + afterCartText);

		// ========== STEP 6: VIEW CART ==========
		logInfo("========================================");
		logInfo("STEP 6: VIEW CART - Verify cart contents");
		logInfo("========================================");

		logInfo("Navigating to cart page");
		productPage.clickViewCart();
		logInfo("✓ Cart page loaded");

		CartPage cartPage = new CartPage(driver);

		Assert.assertTrue(cartPage.isCartPageDisplayed(), "Cart page should be displayed");
		logInfo("✓ Cart page verified");

		Assert.assertTrue(cartPage.isProductDisplayed(), "Product should be visible in cart");
		logInfo("✓ Product visible in cart");

		// ========== STEP 7: PROCEED TO CHECKOUT ==========
		logInfo("========================================");
		logInfo("STEP 7: CHECKOUT - Proceed to checkout");
		logInfo("========================================");

		logInfo("Clicking Checkout button");
		cartPage.clickCheckout();
		logInfo("✓ Checkout clicked");

		CheckoutPage checkoutPage = new CheckoutPage(driver);
		Assert.assertTrue(checkoutPage.isCheckoutPage(), "Checkout page should be displayed");
		logInfo("✓ Checkout page loaded");

		// ========== STEP 8: VERIFY CHECKOUT ==========
		logInfo("========================================");
		logInfo("STEP 8: VERIFY - Checkout cart total");
		logInfo("========================================");

		String totalText = checkoutPage.getCartTotal();
		Assert.assertTrue(totalText.contains("$"), "Cart total should display dollar amount");
		logInfo("✓ Checkout cart total: " + totalText);

		// ========== TEST COMPLETE ==========
		logInfo("========================================");
		logInfo("========== END-TO-END TEST COMPLETED SUCCESSFULLY ==========");
		logInfo("========================================");
		logInfo("User journey verified:");
		logInfo("  ✓ Register → Login → Search → Add to Cart → View Cart → Checkout");
		logInfo("========================================");
		logInfo("TEST PASSED: completeUserJourneyTest (E2E)");
		logInfo("========================================");
	}
}