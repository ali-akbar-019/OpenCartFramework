package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MyAccountPage {

	WebDriver driver;

	public MyAccountPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	// ---------------- LOCATORS ----------------

	@FindBy(xpath = "//h1[normalize-space()='My Account']")
	WebElement myAccountHeading;

	@FindBy(xpath = "//span[normalize-space()='My Account']")
	WebElement myAccountMenu;

	@FindBy(xpath = "//a[normalize-space()='Edit your account information']")
	WebElement editAccountLink;

	@FindBy(xpath = "//a[normalize-space()='Change your password']")
	WebElement changePasswordLink;

	@FindBy(xpath = "//a[normalize-space()='Modify your address book entries']")
	WebElement addressBookLink;

	@FindBy(xpath = "//a[normalize-space()='Modify your wish list']")
	WebElement wishlistLink;

	@FindBy(xpath = "//a[normalize-space()='View your order history']")
	WebElement orderHistoryLink;

	@FindBy(xpath = "//a[normalize-space()='Downloads']")
	WebElement downloadsLink;

	@FindBy(xpath = "//a[normalize-space()='Subscribe / unsubscribe to newsletter']")
	WebElement newsletterLink;

	@FindBy(xpath = "//a[normalize-space()='Logout']")
	WebElement logoutLink;

	// ---------------- ACTIONS ----------------

	public boolean isMyAccountPageDisplayed() {
		return myAccountHeading.isDisplayed();
	}

	public String getMyAccountHeadingText() {
		return myAccountHeading.getText();
	}

	public void openMyAccountMenu() {
		myAccountMenu.click();
	}

	public void clickEditAccount() {
		editAccountLink.click();
	}

	public void clickChangePassword() {
		changePasswordLink.click();
	}

	public void clickAddressBook() {
		addressBookLink.click();
	}

	public void clickWishlist() {
		wishlistLink.click();
	}

	public void clickOrderHistory() {
		orderHistoryLink.click();
	}

	public void clickDownloads() {
		downloadsLink.click();
	}

	public void clickNewsletter() {
		newsletterLink.click();
	}

	public void clickLogout() {
		myAccountMenu.click();
		logoutLink.click();
	}
}