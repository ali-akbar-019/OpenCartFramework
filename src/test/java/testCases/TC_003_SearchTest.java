package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.SearchPage;
import utilities.DataProviders;

public class TC_003_SearchTest extends BaseClass {

	@Test(dataProvider = "SearchData", dataProviderClass = DataProviders.class, groups = { "smoke",
			"regression" }, description = "Search functionality validation (valid + invalid cases)")
	public void searchTest(String testCase, String searchText, String expected) {

		logInfo("========================================");
		logInfo("TEST STARTED: searchTest - " + testCase);
		logInfo("========================================");

		logInfo("Test Data - Search Text: " + searchText + " | Expected: " + expected);

		logInfo("STEP 1: Navigating to Home page and performing search");
		HomePage homePage = new HomePage(driver);

		logInfo("Searching for: '" + searchText + "'");
		homePage.searchFor(searchText);
		logInfo("✓ Search submitted");

		logInfo("STEP 2: Getting search results");
		SearchPage searchPage = new SearchPage(driver);

		boolean result;

		// VALID CASE
		if (expected.equalsIgnoreCase("pass")) {

			logInfo("STEP 3: Valid search validation started");
			logInfo("Expected: Search should return at least 1 product");

			int resultCount = searchPage.getSearchResultCount();
			logInfo("✓ Results found: " + resultCount);

			result = resultCount > 0;

			Assert.assertTrue(result, "Expected results but found none for: " + searchText);
			logInfo("✓ Valid search successful - Products found for: " + searchText);

			logInfo("========================================");
			logInfo("TEST PASSED: searchTest - " + testCase);
			logInfo("Search returned " + resultCount + " result(s)");
			logInfo("========================================");
		}

		// INVALID CASE
		else {

			logInfo("STEP 3: Invalid search validation started");
			logInfo("Expected: 'No results' message should be displayed");

			result = searchPage.isNoResultMessageDisplayed();

			Assert.assertTrue(result, "Expected 'No results' message for: " + searchText);
			logInfo("✓ 'No results' message displayed correctly for: " + searchText);

			logInfo("========================================");
			logInfo("TEST PASSED: searchTest - " + testCase);
			logInfo("Invalid search handled correctly - No results shown");
			logInfo("========================================");
		}
	}
}