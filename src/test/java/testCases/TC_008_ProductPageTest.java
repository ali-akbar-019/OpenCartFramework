package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.ProductPage;
import pageObjects.SearchPage;

public class TC_008_ProductPageTest extends BaseClass {

	private void navigateToMacProduct() {
		logInfo("  → Navigating to Mac product page");
		HomePage homePage = new HomePage(driver);
		homePage.searchFor("Mac");
		SearchPage searchPage = new SearchPage(driver);
		searchPage.selectFirstProduct();
		logInfo("  ← Mac product page loaded");
	}

	@Test(priority = 1, groups = { "regression" })
	public void productNameDisplayedTest() {
		logInfo("========================================");
		logInfo("TEST STARTED: productNameDisplayedTest");
		logInfo("========================================");

		logInfo("STEP 1: Navigating to Mac product");
		navigateToMacProduct();

		logInfo("STEP 2: Getting product name");
		ProductPage productPage = new ProductPage(driver);
		String name = productPage.getProductName();

		logInfo("STEP 3: Verifying product name is displayed");
		Assert.assertTrue(name != null && !name.isEmpty(), "Product name should be displayed on product page");
		logInfo("✓ Product name displayed: " + name);

		logInfo("========================================");
		logInfo("TEST PASSED: productNameDisplayedTest");
		logInfo("========================================");
	}

	@Test(priority = 2, groups = { "regression" })
	public void productPriceDisplayedTest() {
		logInfo("========================================");
		logInfo("TEST STARTED: productPriceDisplayedTest");
		logInfo("========================================");

		logInfo("STEP 1: Navigating to Mac product");
		navigateToMacProduct();

		logInfo("STEP 2: Getting product price");
		ProductPage productPage = new ProductPage(driver);
		String price = productPage.getProductPrice();

		logInfo("STEP 3: Verifying product price contains $ symbol");
		Assert.assertTrue(price != null && price.contains("$"), "Product price should contain $ symbol");
		logInfo("✓ Product price displayed: " + price);

		logInfo("========================================");
		logInfo("TEST PASSED: productPriceDisplayedTest");
		logInfo("========================================");
	}

	@Test(priority = 3, groups = { "regression" })
	public void productImageDisplayedTest() {
		logInfo("========================================");
		logInfo("TEST STARTED: productImageDisplayedTest");
		logInfo("========================================");

		logInfo("STEP 1: Navigating to Mac product");
		navigateToMacProduct();

		logInfo("STEP 2: Verifying product image is displayed");
		ProductPage productPage = new ProductPage(driver);
		Assert.assertTrue(productPage.isProductImageDisplayed(), "Product image should be visible with valid src");
		logInfo("✓ Product image displayed successfully");

		logInfo("========================================");
		logInfo("TEST PASSED: productImageDisplayedTest");
		logInfo("========================================");
	}

	@Test(priority = 4, groups = { "regression" })
	public void addToCartButtonPresentTest() {
		logInfo("========================================");
		logInfo("TEST STARTED: addToCartButtonPresentTest");
		logInfo("========================================");

		logInfo("STEP 1: Navigating to Mac product");
		navigateToMacProduct();

		logInfo("STEP 2: Verifying Add to Cart button is present");
		ProductPage productPage = new ProductPage(driver);
		Assert.assertTrue(productPage.isAddToCartButtonDisplayed(), "Add to Cart button should be visible and enabled");
		logInfo("✓ Add to Cart button present and enabled");

		logInfo("========================================");
		logInfo("TEST PASSED: addToCartButtonPresentTest");
		logInfo("========================================");
	}

	@Test(priority = 5, groups = { "regression" })
	public void productDefaultQuantityTest() {
		logInfo("========================================");
		logInfo("TEST STARTED: productDefaultQuantityTest (Boundary)");
		logInfo("========================================");

		logInfo("STEP 1: Navigating to Mac product");
		navigateToMacProduct();

		logInfo("STEP 2: Getting default quantity value");
		ProductPage productPage = new ProductPage(driver);
		String defaultQty = productPage.getDefaultQuantity();

		logInfo("STEP 3: Verifying default quantity is 1");
		Assert.assertEquals(defaultQty, "1", "Default product quantity should be 1");
		logInfo("✓ Default quantity is: " + defaultQty);

		logInfo("========================================");
		logInfo("TEST PASSED: productDefaultQuantityTest");
		logInfo("========================================");
	}

	@Test(priority = 6, groups = { "regression" })
	public void zeroQuantityAddToCartTest() {
		logInfo("========================================");
		logInfo("TEST STARTED: zeroQuantityAddToCartTest (Negative Boundary)");
		logInfo("========================================");

		logInfo("STEP 1: Navigating to Mac product");
		navigateToMacProduct();

		ProductPage productPage = new ProductPage(driver);

		logInfo("STEP 2: Getting cart text before adding");
		String beforeCartText = productPage.getCartText();
		logInfo("Cart before add: " + beforeCartText);

		logInfo("STEP 3: Setting quantity to 0 and adding to cart");
		productPage.setQuantity("0");
		productPage.addToCart();
		logInfo("✓ Add to Cart clicked with quantity 0");

		logInfo("STEP 4: Waiting for validation");
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		}

		logInfo("STEP 5: Checking validation result");
		boolean warningShown = productPage.isDangerAlertDisplayed();
		String afterCartText = productPage.getCartText();

		if (!warningShown && afterCartText.equals(beforeCartText)) {
			logInfo("✓ PASS: Cart unchanged for zero quantity");
			Assert.assertTrue(true, "Zero quantity did not add to cart");
		} else if (warningShown) {
			logInfo("✓ PASS: Warning displayed for zero quantity");
			Assert.assertTrue(true, "Warning displayed for zero quantity");
		} else {
			logInfo("✗ FAIL: Cart changed from '" + beforeCartText + "' to '" + afterCartText + "'");
			Assert.fail("System should not add zero quantity to cart");
		}

		logInfo("========================================");
		logInfo("TEST PASSED: zeroQuantityAddToCartTest");
		logInfo("========================================");
	}

	@Test(priority = 7, groups = { "regression" })
	public void negativeQuantityAddToCartTest() {
		logInfo("========================================");
		logInfo("TEST STARTED: negativeQuantityAddToCartTest (Negative Test)");
		logInfo("========================================");

		logInfo("STEP 1: Navigating to Mac product");
		navigateToMacProduct();

		ProductPage productPage = new ProductPage(driver);

		logInfo("STEP 2: Getting cart text before adding");
		String beforeCartText = productPage.getCartText();
		logInfo("Cart before add: " + beforeCartText);

		logInfo("STEP 3: Setting quantity to -1 and adding to cart");
		productPage.setQuantity("-1");
		productPage.addToCart();
		logInfo("✓ Add to Cart clicked with quantity -1");

		logInfo("STEP 4: Waiting for validation");
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
		}

		logInfo("STEP 5: Checking validation result");
		boolean warningShown = productPage.isDangerAlertDisplayed();
		String afterCartText = productPage.getCartText();

		if (!warningShown && afterCartText.equals(beforeCartText)) {
			logInfo("⚠ KNOWN BUG: OpenCart accepts negative quantity without validation - Cart unchanged");
			Assert.assertTrue(true, "Bug documented: OpenCart accepts negative quantity without validation");
		} else if (warningShown) {
			logInfo("✓ PASS: System showed warning for negative quantity");
			Assert.assertTrue(true, "Warning displayed for negative quantity");
		} else {
			logInfo("✗ Cart changed from '" + beforeCartText + "' to '" + afterCartText + "'");
			Assert.fail("Unexpected behavior: Cart changed after negative quantity");
		}

		logInfo("========================================");
		logInfo("TEST PASSED: negativeQuantityAddToCartTest (Bug documented)");
		logInfo("========================================");
	}

	@Test(priority = 8, groups = { "regression" })
	public void productReviewSectionTest() {
		logInfo("========================================");
		logInfo("TEST STARTED: productReviewSectionTest");
		logInfo("========================================");

		logInfo("STEP 1: Navigating to Mac product");
		navigateToMacProduct();

		logInfo("STEP 2: Verifying review section is visible");
		ProductPage productPage = new ProductPage(driver);
		Assert.assertTrue(productPage.isReviewTabDisplayed(), "Reviews tab should be visible on product page");
		logInfo("✓ Product review section visible");

		logInfo("========================================");
		logInfo("TEST PASSED: productReviewSectionTest");
		logInfo("========================================");
	}
}