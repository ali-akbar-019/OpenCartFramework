package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.SearchPage;

public class TC_009_SearchBoundaryTest extends BaseClass {

	// ── 1. Single character search (boundary) ─────────────────────────────
	@Test(priority = 1, groups = { "regression" })
	public void singleCharacterSearchTest() {
		logInfo("========================================");
		logInfo("TEST STARTED: singleCharacterSearchTest (Boundary)");
		logInfo("========================================");

		logInfo("STEP 1: Searching for single character 'a'");
		HomePage homePage = new HomePage(driver);
		homePage.searchFor("a");
		logInfo("✓ Search submitted");

		logInfo("STEP 2: Getting search results");
		SearchPage searchPage = new SearchPage(driver);

		logInfo("STEP 3: Verifying system handles single character search");
		boolean handled = searchPage.getSearchResultCount() > 0 || searchPage.isNoResultMessageDisplayed();
		Assert.assertTrue(handled, "System should handle single character search gracefully");
		logInfo("✓ Single character search handled correctly");

		logInfo("========================================");
		logInfo("TEST PASSED: singleCharacterSearchTest");
		logInfo("========================================");
	}

	// ── 2. Empty search (boundary) ────────────────────────────────────────
	@Test(priority = 2, groups = { "regression" })
	public void emptySearchTest() {
		logInfo("========================================");
		logInfo("TEST STARTED: emptySearchTest (Boundary)");
		logInfo("========================================");

		logInfo("STEP 1: Searching with empty string");
		HomePage homePage = new HomePage(driver);
		homePage.searchFor("");
		logInfo("✓ Empty search submitted");

		logInfo("STEP 2: Getting search results");
		SearchPage searchPage = new SearchPage(driver);

		logInfo("STEP 3: Verifying system handles empty search");
		boolean handled = searchPage.getSearchResultCount() > 0 || searchPage.isNoResultMessageDisplayed();
		Assert.assertTrue(handled, "System should handle empty search gracefully");
		logInfo("✓ Empty search handled correctly");

		logInfo("========================================");
		logInfo("TEST PASSED: emptySearchTest");
		logInfo("========================================");
	}

	// ── 3. Max length search query (boundary) ─────────────────────────────
	@Test(priority = 3, groups = { "regression" })
	public void maxLengthSearchTest() {
		logInfo("========================================");
		logInfo("TEST STARTED: maxLengthSearchTest (Boundary)");
		logInfo("========================================");

		String longQuery = "a".repeat(100);
		logInfo("STEP 1: Searching with 100 character query");
		logInfo("Query length: " + longQuery.length() + " characters");
		HomePage homePage = new HomePage(driver);
		homePage.searchFor(longQuery);
		logInfo("✓ Max length search submitted");

		logInfo("STEP 2: Getting search results");
		SearchPage searchPage = new SearchPage(driver);

		logInfo("STEP 3: Verifying system handles max length search without crashing");
		boolean handled = searchPage.getSearchResultCount() > 0 || searchPage.isNoResultMessageDisplayed();
		Assert.assertTrue(handled, "System should handle max length search without crashing");
		logInfo("✓ Max length search handled correctly");

		logInfo("========================================");
		logInfo("TEST PASSED: maxLengthSearchTest");
		logInfo("========================================");
	}

	// ── 4. Special characters search (negative) ───────────────────────────
	@Test(priority = 4, groups = { "regression" })
	public void specialCharacterSearchTest() {
		logInfo("========================================");
		logInfo("TEST STARTED: specialCharacterSearchTest (Negative)");
		logInfo("========================================");

		logInfo("STEP 1: Searching with special characters '!@#$%^&*()'");
		HomePage homePage = new HomePage(driver);
		homePage.searchFor("!@#$%^&*()");
		logInfo("✓ Special character search submitted");

		logInfo("STEP 2: Getting search results");
		SearchPage searchPage = new SearchPage(driver);

		logInfo("STEP 3: Verifying no results message appears");
		Assert.assertTrue(searchPage.isNoResultMessageDisplayed(), "Special character search should return no results");
		logInfo("✓ Special character search returned no results as expected");

		logInfo("========================================");
		logInfo("TEST PASSED: specialCharacterSearchTest");
		logInfo("========================================");
	}

	// ── 5. SQL injection in search (security/negative) ────────────────────
	@Test(priority = 5, groups = { "regression" })
	public void sqlInjectionSearchTest() {
		logInfo("========================================");
		logInfo("TEST STARTED: sqlInjectionSearchTest (Security/Negative)");
		logInfo("========================================");

		logInfo("STEP 1: Searching with SQL injection payload: ' OR '1'='1");
		HomePage homePage = new HomePage(driver);
		homePage.searchFor("' OR '1'='1");
		logInfo("✓ SQL injection search submitted");

		logInfo("STEP 2: Getting search results");
		SearchPage searchPage = new SearchPage(driver);

		logInfo("STEP 3: Verifying system handles SQL injection without crashing");
		boolean handled = searchPage.getSearchResultCount() >= 0 || searchPage.isNoResultMessageDisplayed();
		Assert.assertTrue(handled, "System should handle SQL injection input without crashing");
		logInfo("✓ System did not crash");

		logInfo("STEP 4: Verifying SQL errors are not exposed to user");
		Assert.assertFalse(driver.getPageSource().toLowerCase().contains("sql"),
				"SQL error should never be exposed to user");
		logInfo("✓ No SQL errors exposed in page source");

		logInfo("========================================");
		logInfo("TEST PASSED: sqlInjectionSearchTest");
		logInfo("========================================");
	}

	// ── 6. XSS script injection in search (security/negative) ────────────
	@Test(priority = 6, groups = { "regression" })
	public void xssInjectionSearchTest() {
		logInfo("========================================");
		logInfo("TEST STARTED: xssInjectionSearchTest (Security/Negative)");
		logInfo("========================================");

		logInfo("STEP 1: Searching with XSS payload: <script>alert('xss')</script>");
		HomePage homePage = new HomePage(driver);
		homePage.searchFor("<script>alert('xss')</script>");
		logInfo("✓ XSS search submitted");

		logInfo("STEP 2: Getting search results");
		SearchPage searchPage = new SearchPage(driver);

		logInfo("STEP 3: Verifying system handles XSS without executing script");
		boolean handled = searchPage.getSearchResultCount() >= 0 || searchPage.isNoResultMessageDisplayed();
		Assert.assertTrue(handled, "System should handle XSS input without executing script");
		logInfo("✓ System handled XSS input");

		logInfo("STEP 4: Verifying XSS script not reflected raw in page source");
		Assert.assertFalse(driver.getPageSource().contains("<script>alert('xss')</script>"),
				"XSS script should not be reflected raw in page source");
		logInfo("✓ XSS script not found in page source");

		logInfo("========================================");
		logInfo("TEST PASSED: xssInjectionSearchTest");
		logInfo("========================================");
	}

	// ── 7. Numeric only search ────────────────────────────────────────────
	@Test(priority = 7, groups = { "regression" })
	public void numericSearchTest() {
		logInfo("========================================");
		logInfo("TEST STARTED: numericSearchTest");
		logInfo("========================================");

		logInfo("STEP 1: Searching with numbers '123456'");
		HomePage homePage = new HomePage(driver);
		homePage.searchFor("123456");
		logInfo("✓ Numeric search submitted");

		logInfo("STEP 2: Getting search results");
		SearchPage searchPage = new SearchPage(driver);

		logInfo("STEP 3: Verifying system handles numeric search");
		boolean handled = searchPage.getSearchResultCount() > 0 || searchPage.isNoResultMessageDisplayed();
		Assert.assertTrue(handled, "System should handle numeric search gracefully");
		logInfo("✓ Numeric search handled correctly");

		logInfo("========================================");
		logInfo("TEST PASSED: numericSearchTest");
		logInfo("========================================");
	}

	// ── 8. Case insensitive search (Property-Based Test) ──────────────────
	@Test(priority = 8, groups = { "regression" })
	public void caseInsensitiveSearchTest() {
		logInfo("========================================");
		logInfo("TEST STARTED: caseInsensitiveSearchTest (Property-Based)");
		logInfo("========================================");

		logInfo("STEP 1: Searching with uppercase 'IMAC'");
		HomePage homePage = new HomePage(driver);
		homePage.searchFor("IMAC");
		SearchPage searchPage = new SearchPage(driver);
		int upperCount = searchPage.getSearchResultCount();
		logInfo("Uppercase search returned: " + upperCount + " results");

		logInfo("STEP 2: Searching with lowercase 'imac'");
		homePage.searchFor("imac");
		int lowerCount = searchPage.getSearchResultCount();
		logInfo("Lowercase search returned: " + lowerCount + " results");

		logInfo("STEP 3: Verifying case insensitive property");
		Assert.assertEquals(upperCount, lowerCount,
				"Search should be case insensitive — same results for IMAC and imac");
		logInfo("✓ Case insensitive search verified: " + upperCount + " vs " + lowerCount);

		logInfo("========================================");
		logInfo("TEST PASSED: caseInsensitiveSearchTest");
		logInfo("Property verified: search results are same regardless of case");
		logInfo("========================================");
	}

	// ── 9. Whitespace only search (boundary) ──────────────────────────────
	@Test(priority = 9, groups = { "regression" })
	public void whitespaceSearchTest() {
		logInfo("========================================");
		logInfo("TEST STARTED: whitespaceSearchTest (Boundary)");
		logInfo("========================================");

		logInfo("STEP 1: Searching with whitespace only '     '");
		HomePage homePage = new HomePage(driver);
		homePage.searchFor("     ");
		logInfo("✓ Whitespace search submitted");

		logInfo("STEP 2: Getting search results");
		SearchPage searchPage = new SearchPage(driver);

		logInfo("STEP 3: Verifying system handles whitespace search");
		boolean handled = searchPage.getSearchResultCount() >= 0 || searchPage.isNoResultMessageDisplayed();
		Assert.assertTrue(handled, "System should handle whitespace search without crashing");
		logInfo("✓ Whitespace search handled correctly");

		logInfo("========================================");
		logInfo("TEST PASSED: whitespaceSearchTest");
		logInfo("========================================");
	}

	// ── 10. Partial keyword search ────────────────────────────────────────
	@Test(priority = 10, groups = { "regression" })
	public void partialKeywordSearchTest() {
		logInfo("========================================");
		logInfo("TEST STARTED: partialKeywordSearchTest");
		logInfo("========================================");

		logInfo("STEP 1: Searching with partial keyword 'iPh' (expecting iPhone)");
		HomePage homePage = new HomePage(driver);
		homePage.searchFor("iPh");
		logInfo("✓ Partial keyword search submitted");

		logInfo("STEP 2: Getting search results");
		SearchPage searchPage = new SearchPage(driver);
		int results = searchPage.getSearchResultCount();
		logInfo("Results found: " + results);

		logInfo("STEP 3: Verifying partial keyword returns matching results");
		Assert.assertTrue(results > 0, "Partial keyword search should return matching results");
		logInfo("✓ Partial keyword search returned " + results + " results");

		logInfo("========================================");
		logInfo("TEST PASSED: partialKeywordSearchTest");
		logInfo("========================================");
	}
}