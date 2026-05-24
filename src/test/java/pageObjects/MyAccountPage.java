package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MyAccountPage {

	WebDriver driver;

	@FindBy(xpath = "//h1[normalize-space()='My Account']")
	WebElement myAccountHeading;

	@FindBy(xpath = "//span[text()='My Account']")
	WebElement myAccount;

	@FindBy(xpath = "//a[@class='dropdown-item' and normalize-space()='Logout']")
	WebElement logoutLink;

	@FindBy(xpath = "//a[normalize-space()='Edit your account information']")
	WebElement editAccountLink;

	@FindBy(xpath = "//a[normalize-space()='Change your password']")
	WebElement changePasswordLink;

	public MyAccountPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public boolean isMyAccountPageDisplayed() {
		return myAccountHeading.isDisplayed();
	}

	public String getMyAccountHeading() {
		return myAccountHeading.getText();
	}

	public void clickMyAccount() {
		myAccount.click();
	}

	public void clickLogout() {
		clickMyAccount();
		logoutLink.click();
	}

	public void clickEditAccount() {
		editAccountLink.click();
	}
}