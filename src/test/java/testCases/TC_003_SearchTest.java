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

		logInfo("Starting search test: " + testCase);

		HomePage homePage = new HomePage(driver);

		logInfo("Searching for: " + searchText);
		homePage.searchFor(searchText);

		SearchPage searchPage = new SearchPage(driver);

		boolean result;

		// VALID CASE
		if (expected.equalsIgnoreCase("pass")) {

			logInfo("Valid search validation");

			int resultCount = searchPage.getSearchResultCount();
			logInfo("Results found: " + resultCount);

			result = resultCount > 0;

			Assert.assertTrue(result, "Expected results but found none for: " + searchText);
		}

		// INVALID CASE
		else {

			logInfo("Invalid search validation");

			result = searchPage.isNoResultMessageDisplayed();

			Assert.assertTrue(result, "Expected 'No results' message for: " + searchText);
		}

		logInfo("Search test completed: " + testCase);
	}
}