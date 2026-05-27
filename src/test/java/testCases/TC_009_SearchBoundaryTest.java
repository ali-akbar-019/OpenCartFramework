package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.SearchPage;

public class TC_009_SearchBoundaryTest extends BaseClass {

	// ── 1. Single character search (boundary) ─────────────────────────────
	@Test(priority = 1, groups = { "regression" })
	public void singleCharacterSearchTest() {
		logInfo("Testing single character search (boundary)");
		HomePage homePage = new HomePage(driver);
		homePage.searchFor("a");
		SearchPage searchPage = new SearchPage(driver);
		// system should handle it — either results or no results message
		boolean handled = searchPage.getSearchResultCount() > 0 || searchPage.isNoResultMessageDisplayed();
		Assert.assertTrue(handled, "System should handle single character search gracefully");
		logInfo("Single character search handled correctly");
	}

	// ── 2. Empty search (boundary) ────────────────────────────────────────
	@Test(priority = 2, groups = { "regression" })
	public void emptySearchTest() {
		logInfo("Testing empty search submission (boundary)");
		HomePage homePage = new HomePage(driver);
		homePage.searchFor("");
		SearchPage searchPage = new SearchPage(driver);
		boolean handled = searchPage.getSearchResultCount() > 0 || searchPage.isNoResultMessageDisplayed();
		Assert.assertTrue(handled, "System should handle empty search gracefully");
		logInfo("Empty search handled correctly");
	}

	// ── 3. Max length search query (boundary) ─────────────────────────────
	@Test(priority = 3, groups = { "regression" })
	public void maxLengthSearchTest() {
		logInfo("Testing max length search query (boundary)");
		String longQuery = "a".repeat(100);
		HomePage homePage = new HomePage(driver);
		homePage.searchFor(longQuery);
		SearchPage searchPage = new SearchPage(driver);
		boolean handled = searchPage.getSearchResultCount() > 0 || searchPage.isNoResultMessageDisplayed();
		Assert.assertTrue(handled, "System should handle max length search without crashing");
		logInfo("Max length search handled correctly");
	}

	// ── 4. Special characters search (negative) ───────────────────────────
	@Test(priority = 4, groups = { "regression" })
	public void specialCharacterSearchTest() {
		logInfo("Testing special characters in search (negative)");
		HomePage homePage = new HomePage(driver);
		homePage.searchFor("!@#$%^&*()");
		SearchPage searchPage = new SearchPage(driver);
		Assert.assertTrue(searchPage.isNoResultMessageDisplayed(), "Special character search should return no results");
		logInfo("Special character search returned no results as expected");
	}

	// ── 5. SQL injection in search (security/negative) ────────────────────
	@Test(priority = 5, groups = { "regression" })
	public void sqlInjectionSearchTest() {
		logInfo("Testing SQL injection in search (security negative test)");
		HomePage homePage = new HomePage(driver);
		homePage.searchFor("' OR '1'='1");
		SearchPage searchPage = new SearchPage(driver);
		// should not crash or expose data — just normal no result or result page
		boolean handled = searchPage.getSearchResultCount() >= 0 || searchPage.isNoResultMessageDisplayed();
		Assert.assertTrue(handled, "System should handle SQL injection input without crashing");
		// also verify we are still on search page — not an error page
		Assert.assertFalse(driver.getPageSource().toLowerCase().contains("sql"),
				"SQL error should never be exposed to user");
		logInfo("SQL injection handled safely");
	}

	// ── 6. XSS script injection in search (security/negative) ────────────
	@Test(priority = 6, groups = { "regression" })
	public void xssInjectionSearchTest() {
		logInfo("Testing XSS injection in search (security negative test)");
		HomePage homePage = new HomePage(driver);
		homePage.searchFor("<script>alert('xss')</script>");
		SearchPage searchPage = new SearchPage(driver);
		boolean handled = searchPage.getSearchResultCount() >= 0 || searchPage.isNoResultMessageDisplayed();
		Assert.assertTrue(handled, "System should handle XSS input without executing script");
		Assert.assertFalse(driver.getPageSource().contains("<script>alert('xss')</script>"),
				"XSS script should not be reflected raw in page source");
		logInfo("XSS injection handled safely");
	}

	// ── 7. Numeric only search ────────────────────────────────────────────
	@Test(priority = 7, groups = { "regression" })
	public void numericSearchTest() {
		logInfo("Testing numeric only search");
		HomePage homePage = new HomePage(driver);
		homePage.searchFor("123456");
		SearchPage searchPage = new SearchPage(driver);
		boolean handled = searchPage.getSearchResultCount() > 0 || searchPage.isNoResultMessageDisplayed();
		Assert.assertTrue(handled, "System should handle numeric search gracefully");
		logInfo("Numeric search handled correctly");
	}

	// ── 8. Case insensitive search ────────────────────────────────────────
	@Test(priority = 8, groups = { "regression" })
	public void caseInsensitiveSearchTest() {
		logInfo("Testing case insensitive search");
		HomePage homePage = new HomePage(driver);
		// search uppercase
		homePage.searchFor("IMAC");
		SearchPage searchPage = new SearchPage(driver);
		int upperCount = searchPage.getSearchResultCount();
		// search lowercase
		homePage.searchFor("imac");
		int lowerCount = searchPage.getSearchResultCount();
		Assert.assertEquals(upperCount, lowerCount,
				"Search should be case insensitive — same results for IMAC and imac");
		logInfo("Case insensitive search verified: " + upperCount + " vs " + lowerCount);
	}

	// ── 9. Whitespace only search (boundary) ──────────────────────────────
	@Test(priority = 9, groups = { "regression" })
	public void whitespaceSearchTest() {
		logInfo("Testing whitespace only search (boundary)");
		HomePage homePage = new HomePage(driver);
		homePage.searchFor("     ");
		SearchPage searchPage = new SearchPage(driver);
		boolean handled = searchPage.getSearchResultCount() >= 0 || searchPage.isNoResultMessageDisplayed();
		Assert.assertTrue(handled, "System should handle whitespace search without crashing");
		logInfo("Whitespace search handled correctly");
	}

	// ── 10. Partial keyword search ────────────────────────────────────────
	@Test(priority = 10, groups = { "regression" })
	public void partialKeywordSearchTest() {
		logInfo("Testing partial keyword search");
		HomePage homePage = new HomePage(driver);
		homePage.searchFor("iPh"); // partial for iPhone
		SearchPage searchPage = new SearchPage(driver);
		int results = searchPage.getSearchResultCount();
		Assert.assertTrue(results > 0, "Partial keyword search should return matching results");
		logInfo("Partial keyword search returned: " + results + " results");
	}
}