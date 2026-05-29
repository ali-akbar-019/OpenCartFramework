package testCases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.CartPage;
import pageObjects.HomePage;
import pageObjects.ProductPage;
import pageObjects.SearchPage;

public class TC_005_CartTest extends BaseClass {

	// Helper method to extract numeric cart count from text
	private int getCartItemCount(String cartText) {
		if (cartText == null || cartText.isEmpty()) {
			return 0;
		}
		// Extract number from text like "1 item(s)" or "2 item(s) - $200.00"
		String[] parts = cartText.split(" ");
		try {
			return Integer.parseInt(parts[0]);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	@Test(priority = 1, groups = { "smoke", "regression" })
	public void addProductToCartTest() {

		logInfo("========================================");
		logInfo("TEST STARTED: addProductToCartTest");
		logInfo("========================================");

		logInfo("STEP 1: Searching for product 'Mac'");
		HomePage homePage = new HomePage(driver);
		homePage.searchFor("Mac");
		logInfo("✓ Search completed");

		logInfo("STEP 2: Selecting first product from search results");
		SearchPage searchPage = new SearchPage(driver);
		searchPage.selectFirstProduct();
		logInfo("✓ First product selected");

		logInfo("STEP 3: Getting cart text before adding product");
		ProductPage productPage = new ProductPage(driver);
		String beforeCartText = productPage.getCartText();
		logInfo("Cart before add: " + beforeCartText);

		logInfo("STEP 4: Adding product to cart");
		productPage.addToCart();
		logInfo("✓ Add to Cart button clicked");

		logInfo("STEP 5: Waiting for cart to update");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.cssSelector("#cart-total"), beforeCartText)));
		logInfo("✓ Cart updated");

		logInfo("STEP 6: Verifying cart shows at least 1 item");
		String cartText = productPage.getCartText();
		int itemCount = getCartItemCount(cartText);
		Assert.assertTrue(itemCount >= 1, "Cart should show at least 1 item, but got: " + cartText);
		logInfo("Cart text: " + cartText + " | Item count: " + itemCount);

		logInfo("========================================");
		logInfo("TEST PASSED: addProductToCartTest");
		logInfo("========================================");
	}

	@Test(priority = 2, groups = { "regression" })
	public void addMultipleProductsToCartTest() {

		logInfo("========================================");
		logInfo("TEST STARTED: addMultipleProductsToCartTest");
		logInfo("========================================");

		HomePage homePage = new HomePage(driver);
		ProductPage productPage = new ProductPage(driver);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Product 1
		logInfo("STEP 1: Searching for first product 'Mac'");
		homePage.searchFor("Mac");
		SearchPage searchPage = new SearchPage(driver);
		searchPage.selectFirstProduct();
		logInfo("✓ First product (Mac) selected");

		logInfo("STEP 2: Adding first product to cart");
		String beforeFirstAdd = productPage.getCartText();
		productPage.addToCart();
		wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.cssSelector("#cart-total"), beforeFirstAdd)));
		logInfo("✓ First product (Mac) added to cart");

		// Product 2
		logInfo("STEP 3: Searching for second product 'iPhone'");
		homePage.searchFor("iPhone");
		searchPage.selectFirstProduct();
		logInfo("✓ Second product (iPhone) selected");

		logInfo("STEP 4: Adding second product to cart");
		String beforeSecondAdd = productPage.getCartText();
		productPage.addToCart();
		wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.cssSelector("#cart-total"), beforeSecondAdd)));
		logInfo("✓ Second product (iPhone) added to cart");

		logInfo("STEP 5: Verifying cart shows at least 2 items");
		String cartText = productPage.getCartText();
		int itemCount = getCartItemCount(cartText);
		Assert.assertTrue(itemCount >= 2,
				"Cart should show at least 2 items after adding two products, but got: " + cartText);
		logInfo("Cart text: " + cartText + " | Item count: " + itemCount);

		logInfo("========================================");
		logInfo("TEST PASSED: addMultipleProductsToCartTest");
		logInfo("========================================");
	}

	@Test(priority = 3, groups = { "regression" })
	public void addSameProductTwiceTest() {

		logInfo("========================================");
		logInfo("TEST STARTED: addSameProductTwiceTest");
		logInfo("========================================");

		logInfo("STEP 1: Searching for product 'Mac'");
		HomePage homePage = new HomePage(driver);
		homePage.searchFor("Mac");
		logInfo("✓ Search completed");

		logInfo("STEP 2: Selecting product");
		SearchPage searchPage = new SearchPage(driver);
		searchPage.selectFirstProduct();
		logInfo("✓ Product selected");

		logInfo("STEP 3: Adding same product to cart twice");
		ProductPage productPage = new ProductPage(driver);
		productPage.addToCart();
		logInfo("✓ First add completed");

		productPage.addToCart();
		logInfo("✓ Second add completed");

		logInfo("STEP 4: Verifying cart updated with duplicate product");
		String cartText = productPage.getCartText();
		int itemCount = getCartItemCount(cartText);
		Assert.assertTrue(itemCount >= 1, "Cart should show at least 1 item (with quantity 2), but got: " + cartText);
		logInfo("Cart text after duplicate add: " + cartText + " | Item count: " + itemCount);

		logInfo("========================================");
		logInfo("TEST PASSED: addSameProductTwiceTest");
		logInfo("========================================");
	}

	@Test(priority = 4, groups = { "regression" })
	public void viewCartPageTest() {

		logInfo("========================================");
		logInfo("TEST STARTED: viewCartPageTest");
		logInfo("========================================");

		logInfo("STEP 1: Searching for product 'Mac'");
		HomePage homePage = new HomePage(driver);
		homePage.searchFor("Mac");
		logInfo("✓ Search completed");

		logInfo("STEP 2: Selecting first product");
		SearchPage searchPage = new SearchPage(driver);
		searchPage.selectFirstProduct();
		logInfo("✓ Product selected");

		logInfo("STEP 3: Adding product to cart");
		ProductPage productPage = new ProductPage(driver);
		productPage.addToCart();
		logInfo("✓ Product added to cart");

		logInfo("STEP 4: Navigating to View Cart");
		productPage.clickViewCart();
		logInfo("✓ View Cart clicked");

		logInfo("STEP 5: Verifying Cart page");
		CartPage cartPage = new CartPage(driver);
		Assert.assertTrue(cartPage.isCartPageDisplayed(), "Cart page should be displayed");
		logInfo("✓ Cart page displayed");

		Assert.assertTrue(cartPage.isProductDisplayed(), "Product should be visible in cart");
		logInfo("✓ Product visible in cart");

		logInfo("========================================");
		logInfo("TEST PASSED: viewCartPageTest");
		logInfo("========================================");
	}

	@Test(priority = 5, groups = { "regression" })
	public void removeFromCartTest() {

		logInfo("========================================");
		logInfo("TEST STARTED: removeFromCartTest");
		logInfo("========================================");

		logInfo("STEP 1: Searching for product 'Mac'");
		HomePage homePage = new HomePage(driver);
		homePage.searchFor("Mac");
		logInfo("✓ Search completed");

		logInfo("STEP 2: Selecting first product");
		SearchPage searchPage = new SearchPage(driver);
		searchPage.selectFirstProduct();
		logInfo("✓ Product selected");

		logInfo("STEP 3: Adding product to cart");
		ProductPage productPage = new ProductPage(driver);
		productPage.addToCart();
		logInfo("✓ Product added to cart");

		logInfo("STEP 4: Opening cart");
		productPage.clickViewCart();
		logInfo("✓ Cart opened");

		logInfo("STEP 5: Removing product from cart");
		CartPage cartPage = new CartPage(driver);
		cartPage.removeFromCart();
		logInfo("URL after remove: " + driver.getCurrentUrl());

		String pageSource = driver.getPageSource();
		logInfo("Page contains 'empty': " + pageSource.toLowerCase().contains("empty"));

		logInfo("STEP 6: Verifying cart is empty");
		Assert.assertTrue(cartPage.isCartEmpty(), "Cart should be empty after removal");
		logInfo("✓ Cart is empty after removal");

		logInfo("========================================");
		logInfo("TEST PASSED: removeFromCartTest");
		logInfo("========================================");
	}
}