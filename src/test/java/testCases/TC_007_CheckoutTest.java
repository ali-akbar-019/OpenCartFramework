package testCases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.CheckoutPage;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.ProductPage;
import pageObjects.SearchPage;

public class TC_007_CheckoutTest extends BaseClass {

	private void loginForCheckout() {
		HomePage homePage = new HomePage(driver);
		homePage.navigateToLogin();
		LoginPage loginPage = new LoginPage(driver);
		loginPage.enterEmail(p.getProperty("username"));
		loginPage.enterPassword(p.getProperty("password"));
		loginPage.clickLogin();
		
		// FIXED: Replaced Thread.sleep with WebDriverWait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.urlContains("account"));
	}

	private void addMacToCart() {
		HomePage homePage = new HomePage(driver);
		homePage.searchFor("Mac");
		SearchPage searchPage = new SearchPage(driver);
		searchPage.selectFirstProduct();
		ProductPage productPage = new ProductPage(driver);
		productPage.addToCart();
		
		// FIXED: Replaced Thread.sleep with WebDriverWait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.textToBePresentInElementLocated(
			By.cssSelector("#cart-total"), "item"));
		
		productPage.clickViewCart();
	}

	@Test(priority = 1, groups = { "regression" })
	public void guestCheckoutRedirectTest() {
		logInfo("Testing guest checkout redirect");
		HomePage homePage = new HomePage(driver);
		homePage.searchFor("Mac");
		new SearchPage(driver).selectFirstProduct();
		ProductPage productPage = new ProductPage(driver);
		productPage.addToCart();
		
		// Wait for cart to update before clicking view cart
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.textToBePresentInElementLocated(
			By.cssSelector("#cart-total"), "item"));
		
		productPage.clickViewCart();
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		checkoutPage.clickCheckout();
		String currentUrl = driver.getCurrentUrl();
		Assert.assertTrue(currentUrl.contains("checkout") || currentUrl.contains("login"),
				"Guest should be redirected to checkout or login page");
		logInfo("Guest checkout redirect working: " + currentUrl);
	}

	@Test(priority = 2, groups = { "regression" })
	public void loggedInUserCheckoutTest() {
		logInfo("Testing checkout access for logged-in user");
		loginForCheckout();
		addMacToCart();
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		checkoutPage.clickCheckout();
		
		// Wait for checkout page to load
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.urlContains("checkout"));
		
		Assert.assertTrue(checkoutPage.isCheckoutPage(), "Logged-in user should reach checkout page");
		logInfo("Checkout page reached: " + driver.getCurrentUrl());
	}

	@Test(priority = 3, groups = { "regression" })
	public void cartTotalDisplayedTest() {
		logInfo("Testing cart total is displayed");
		loginForCheckout();
		addMacToCart();
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		checkoutPage.clickCheckout();
		
		// Wait for cart total to be visible
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".table-bordered")));
		
		String totalText = checkoutPage.getCartTotal();
		Assert.assertTrue(totalText.contains("$"), "Cart total should display a dollar amount");
		logInfo("Cart total displayed: " + totalText);
	}

	@Test(priority = 4, groups = { "regression" })
	public void cartQuantityUpdateTest() {
		logInfo("Testing cart quantity update");
		loginForCheckout();
		addMacToCart();
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		checkoutPage.updateQuantity("2");
		
		// FIXED: Replaced Thread.sleep with WebDriverWait
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.attributeToBe(
			By.cssSelector(".quantity-input"), "value", "2"));
		
		String qty = checkoutPage.getQuantityValue();
		Assert.assertEquals(qty, "2", "Cart quantity should be updated to 2");
		logInfo("Cart quantity updated successfully");
	}

	@Test(priority = 5, groups = { "regression" })
	public void emptyCartCheckoutTest() {
		logInfo("Testing checkout with empty cart");
		loginForCheckout();
		driver.get(p.getProperty("appURL") + "index.php?route=checkout/checkout");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
		String pageSource = driver.getPageSource().toLowerCase();
		String currentUrl = driver.getCurrentUrl();
		boolean properlyHandled = pageSource.contains("empty") || pageSource.contains("no products")
				|| !currentUrl.contains("checkout/checkout");
		if (!properlyHandled) {
			logInfo("BUG FOUND: OpenCart allows access to checkout with empty cart at: " + currentUrl);
		}
		Assert.assertFalse(properlyHandled, "KNOWN BUG: System should block empty cart checkout but does not");
	}
}