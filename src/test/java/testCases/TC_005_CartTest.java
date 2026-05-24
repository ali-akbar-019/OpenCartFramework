package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.CartPage;
import pageObjects.HomePage;
import pageObjects.ProductPage;
import pageObjects.SearchPage;

public class TC_005_CartTest extends BaseClass {

	@Test(priority = 1, groups = { "smoke", "regression" })
	public void addProductToCartTest() {

		logInfo("Starting Add to Cart Test");

		HomePage homePage = new HomePage(driver);
		homePage.searchFor("Mac");

		SearchPage searchPage = new SearchPage(driver);
		searchPage.selectFirstProduct();

		ProductPage productPage = new ProductPage(driver);
		productPage.addToCart();

		logInfo("Product added to cart");

		Assert.assertTrue(productPage.getCartText().contains("item"), "Cart should show item added");

		logInfo("Add to cart test passed");
	}

	@Test(priority = 2, groups = { "regression" })
	public void addMultipleProductsToCartTest() {

		logInfo("Starting Multiple Add to Cart Test");

		HomePage homePage = new HomePage(driver);

		// Product 1
		homePage.searchFor("Mac");
		SearchPage searchPage = new SearchPage(driver);
		searchPage.selectFirstProduct();

		ProductPage productPage = new ProductPage(driver);
		productPage.addToCart();

		// Product 2
		homePage.searchFor("iPhone");
		searchPage.selectFirstProduct();
		productPage.addToCart();

		logInfo("Multiple products added");

		Assert.assertTrue(productPage.getCartText().contains("item"), "Cart should reflect multiple items");
	}

	@Test(priority = 3, groups = { "regression" })
	public void addSameProductTwiceTest() {

		logInfo("Starting duplicate product test");

		HomePage homePage = new HomePage(driver);
		homePage.searchFor("Mac");

		SearchPage searchPage = new SearchPage(driver);
		searchPage.selectFirstProduct();

		ProductPage productPage = new ProductPage(driver);

		productPage.addToCart();
		productPage.addToCart();

		logInfo("Same product added twice");

		Assert.assertTrue(productPage.getCartText().contains("item"), "Cart should handle quantity correctly");
	}

	@Test(priority = 4, groups = { "regression" })
	public void viewCartPageTest() {

		logInfo("Starting View Cart Test");

		HomePage homePage = new HomePage(driver);
		homePage.searchFor("Mac");

		SearchPage searchPage = new SearchPage(driver);
		searchPage.selectFirstProduct();

		ProductPage productPage = new ProductPage(driver);
		productPage.addToCart();

		logInfo("Navigating to View Cart");

		productPage.clickViewCart();

		CartPage cartPage = new CartPage(driver);

		Assert.assertTrue(cartPage.isCartPageDisplayed(), "Cart page should be displayed");
		Assert.assertTrue(cartPage.isProductDisplayed(), "Product should be visible in cart");

		logInfo("View Cart test passed");
	}

	@Test(priority = 5, groups = { "regression" })
	public void removeFromCartTest() {

		logInfo("Starting Remove From Cart Test");

		HomePage homePage = new HomePage(driver);
		homePage.searchFor("Mac");

		SearchPage searchPage = new SearchPage(driver);
		searchPage.selectFirstProduct();

		ProductPage productPage = new ProductPage(driver);
		productPage.addToCart();

		logInfo("Opening cart and removing product");

		productPage.clickViewCart();

		CartPage cartPage = new CartPage(driver);
		cartPage.removeFromCart();
		Assert.assertTrue(cartPage.isCartEmpty(), "Cart should be empty after removal");

		logInfo("Remove from cart test passed");
	}

}