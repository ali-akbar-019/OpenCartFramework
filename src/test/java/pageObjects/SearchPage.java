package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SearchPage {

	WebDriver driver;

	@FindBy(xpath = "//div[@id='product-list']")
	List<WebElement> searchResults;

	@FindBy(xpath = "//p[contains(text(),'There is no product that matches the search criter')]")
	WebElement noResultMessage;

	@FindBy(css = "h1")
	WebElement searchHeading;

	public SearchPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public int getSearchResultCount() {
		return searchResults.size();
	}

	public List<String> getProductNames() {
		List<String> names = new ArrayList<>();
		for (WebElement result : searchResults) {
			names.add(result.getText());
		}
		return names;
	}

	public boolean isNoResultMessageDisplayed() {
		try {
			return noResultMessage.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}
}