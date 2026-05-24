package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {

	WebDriver driver;

	@FindBy(xpath = "//span[text()='My Account']")
	WebElement myAccountDropMenu;

	@FindBy(linkText = "Login")
	WebElement loginOption;

	@FindBy(linkText = "Register")
	WebElement registerOption;

	@FindBy(xpath = "//input[@placeholder='Search']")
	WebElement searchBox;

	@FindBy(xpath = "//button[@class='btn btn-light btn-lg']")
	WebElement searchButton;

	// constructor
	public HomePage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void clickMyAccount() {
		myAccountDropMenu.click();
	}

	public void selectLogin() {
		loginOption.click();
	}

	public void selectRegister() {
		registerOption.click();
	}

	// combined navigation methods
	public void navigateToLogin() {
		clickMyAccount();
		selectLogin();
	}

	public void navigateToRegister() {
		clickMyAccount();
		selectRegister();
	}

	public void searchFor(String keyword) {
		searchBox.clear();
		searchBox.sendKeys(keyword);
		searchButton.click();
	}
}