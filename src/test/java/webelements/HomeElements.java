package webelements;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import base.BaseClass;

public class HomeElements extends BaseClass {

	@FindBy(xpath = "//span[text()=\"What's New\"]")
	private WebElement whatsNew;

	@FindBy(xpath = "//span[text()=\"Women\"]")
	private WebElement women;

	@FindBy(xpath = "//span[text()=\"Men\"]")
	private WebElement men;

	public WebElement men() {
		return men;
	}

	@FindBy(xpath = "//a[contains(@href,\"men/tops-men.html\")]")
	private WebElement topsMen;

	public WebElement topsMen() {
		return topsMen;
	}

	@FindBy(xpath = "//a[@id=\"ui-id-19\"]")
	private WebElement menJacket;

	public WebElement menJacket() {
		return menJacket;

	}

	public WebElement selectColor(String color) {
		String first = "//div[@aria-label=\"";
		String second = "\"]";
		WebElement selectColor = driver.findElement(By.xpath(first + color + second));
		System.out.println(first + color + second);
		return selectColor;
	}

	public WebElement selectSize(String size) {

		String first = "//div[@aria-label=\"";
		String second = "\"]";
		WebElement selectSize = driver.findElement(By.xpath(first + size + second));
		System.out.println(first + size + second);
		return selectSize;
	}

//	

	@FindBy(xpath = "//div[@aria-label=\"Black\"]")
	private WebElement blackColor;

	public WebElement blackColor() {
		return blackColor;
	}

	@FindBy(xpath = "//div[@aria-label=\"XL\"]")
	private WebElement xL;

	public WebElement xL() {
		return xL;
	}

	@FindBy(xpath = "//button[@title=\"Add to Cart\"]")
	private WebElement addToCart;

	public WebElement addToCart() {
		return addToCart;
	}

	@FindBy(xpath = "//span[text()=\"Gear\"]")
	private WebElement gear;

	@FindBy(xpath = "//span[text()=\"Training\"]")
	private WebElement training;

	@FindBy(xpath = "//span[text()=\"Sale\"]")
	private WebElement sale;

	@FindBy(xpath = "//input[@id=\"search\"]")
	private WebElement search;

	@FindBy(xpath = "//a[@data-bind=\"scope: 'minicart_content'\"]")
	private WebElement cart;

	@FindBy(xpath = "//span[@class=\"counter-number\"]")
	private WebElement cartCount;

	public WebElement cartCount() {
		return cartCount;
	}

	@FindBy(css = "#maincontent > div.page.messages > div:nth-child(2) > div > div > div")
	private WebElement messageBox;

	public WebElement messageBox() {
		return messageBox;
	}

	@FindBy(xpath = "//span[text()=\"Shop New Yoga\"]")
	private WebElement shopNewYoga;

	@FindBy(xpath = "//span[text()=\"Shop Pants\"]")
	private WebElement shopPants;

	@FindBy(xpath = "//span[text()=\"Shop Tees\"]")
	private WebElement tees;

	public WebElement search() {
		return search;
	}

	public WebElement shopNewYoga() {
		return shopNewYoga;
	}

	@FindBy(xpath = "//a[text()=\"shopping cart\"]")
	private WebElement shoppingCart;

	public WebElement shoppingCart() {
		return shoppingCart;
	}

	@FindBy(xpath = "//*[@id=\"shopping-cart-table\"]/tbody/tr[2]/td/div/a[2]")
	private WebElement removeItem;

	public WebElement removeItem() {
		return removeItem;
	}

	@FindBy(xpath = "//div[@class=\"loading-mask\"]")
	private WebElement cartLoader;

	public WebElement cartLoader() {
		return cartLoader;
	}

	public int getCartCount() {
		int cartCount = 0;

		// Wait for the cart loader to disappear
		// explictWait(Duration.ofSeconds(15)).until(ExpectedConditions.invisibilityOf(cartLoader()));

		// fluentWait(Duration.ofSeconds(15),Duration.ofMillis(1)).until(ExpectedConditions.invisibilityOf(cartLoader()));

		// Get the cart count as a string
		String s = cartCount().getText().trim(); // Use trim() to remove any leading/trailing spaces

		System.out.println("Cart count is: " + s);

		// Check if the string is empty or null
		if (s == null || s.isEmpty()) {
			cartCount = 0; // If the string is empty or null, set cart count to 0
		} else {
			try {
				cartCount = Integer.parseInt(s); // Try to parse the string to an integer
			} catch (NumberFormatException e) {
				System.out.println("Failed to parse cart count, setting cart count to 0.");
				cartCount = 0; // If parsing fails, set cart count to 0
			}
		}

		return cartCount;
	}

	@FindBy(xpath = "//a[@class=\"action showcart\"]")
	private WebElement viewCart;

	public WebElement viewCart() {
		return viewCart;
	}

	@FindBy(xpath = "//span[text()=\"View and Edit Cart\"]")
	private WebElement viewEditCart;

	public WebElement viewEditCart() {
		return viewEditCart;
	}

	@FindBy(xpath = "//input[@data-role=\"cart-item-qty\"]")
	private WebElement quantityInputField;

	public WebElement quantityInputField() {
		return quantityInputField;
	}

	@FindBy(xpath = "//button[@value=\"update_qty\"]")
	private WebElement updateCart;

	public WebElement updateCart() {
		return updateCart;
	}

	@FindBy(xpath = "//div[@class=\"mage-error\"]")
	private WebElement quantityError;

	public WebElement quantityError() {
		return quantityError;
	}

	@FindBy(xpath = "//h1[contains(text(), 'Attention')]/ancestor::aside//div[contains(@class, 'modal-content')]")
	private WebElement alertText;

	public WebElement alertText() {
		return alertText;
	}

	@FindBy(xpath = "//h1[contains(text(), 'Attention')]/ancestor::aside//button[@class='action-primary action-accept' and @data-role='action']")
	private WebElement alertOkButton;

	public WebElement alertOkButton() {
		return alertOkButton;
	}

	public Double addAllAmountInCart() {

		List<WebElement> subTotal = driver
				.findElements(By.xpath("//td[@class=\"col subtotal\"]//span[@class=\"cart-price\"]//span"));
		List<Double> amount = new ArrayList<>();
		double totalSum = 0;
		for (WebElement totalAmount : subTotal) {
			String amountText = totalAmount.getText().replace("$", ""); // Remove the '$' symbol
			double amountValue = Double.parseDouble(amountText); // Convert the string to a double
			amount.add(amountValue);
			totalSum += amountValue; // Add the value to the total sum
		}
		return totalSum; // Return the total sum
	}

	public Double subTotal() {

		String sub = driver.findElement(By.xpath("//td[@class=\"amount\"]//span[@data-th=\"Subtotal\"]")).getText();
		Double subTotal = Double.parseDouble(sub.replace("$", ""));
		return subTotal;
	}

	public Double discount() {
		String dis = driver.findElement(By.xpath("//td[@class=\"amount\"]//span//span")).getText();
		Double discount = Double.parseDouble(dis.replace("-$", ""));
		return discount;

	}

	public Double tax() {
		String dx = driver.findElement(By.xpath("//td[@data-th=\"Tax\"]//span")).getText();
		Double tax = Double.parseDouble(dx.replace("$", ""));
		return tax;

	}

	public Double orderTotal() {
		String tot = driver.findElement(By.xpath("//td[@data-th=\"Order Total\"]//span")).getText();
		Double orderTotal = Double.parseDouble(tot.replace("$", ""));
		return orderTotal;

	}

	@FindBy(xpath = "//div[@class=\"ea-placement ea-type-image\"]")
	private WebElement googleAds;

	@FindBy(xpath = "//div[@class=\"ea-stickybox-hide\"]")
	private WebElement closeGoogleAds;

	public void closeGoogleAds() {
		try {
			fluentWait(Duration.ofSeconds(2), Duration.ofNanos(1)).until(ExpectedConditions.visibilityOf(googleAds));
			// Close the ad if present
			if (closeGoogleAds.isDisplayed()) {
				closeGoogleAds.click();
			}
		} catch (NoSuchElementException e) {
			// Handle the case where the ad or close button is not found
			System.out.println("Google ad or close button not found. It might not be present on this page.");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@FindBy(xpath = "//span[text()=\"Proceed to Checkout\"]")
	private WebElement proceedToCheckOut;

	public WebElement proceedToCheckOut() {

		return proceedToCheckOut;
	}

	@FindBy(xpath = "//input[@id=\"customer-email\"]")
	private WebElement emailId;

	public WebElement emailId() {
		return emailId;
	}

	@FindBy(xpath = "//input[@name=\"firstname\"]")
	private WebElement firstName;

	public WebElement firstName() {

		return firstName;
	}

	@FindBy(xpath = "//input[@name=\"lastname\"]")
	private WebElement lastName;

	public WebElement lastName() {
		return lastName;
	}

	@FindBy(xpath = "//input[@name=\"street[0]\"]")
	private WebElement streetName;

	public WebElement streetName() {
		return streetName;
	}

	@FindBy(xpath = "//input[@name=\"city\"]")
	private WebElement cityName;

	public WebElement cityName() {
		return cityName;
	}

	@FindBy(xpath = "//input[@name=\"postcode\"]")
	private WebElement postCode;

	public WebElement postCode() {
		return postCode;
	}

	@FindBy(xpath = "//select[@name=\"region_id\"]")
	private WebElement stateName;

	public WebElement stateName() {
		return stateName;
	}

	@FindBy(xpath = "//select[@name=\"region_id\"]")
	private WebElement countryName;

	public WebElement countryName() {
		return countryName;
	}

	@FindBy(xpath = "//input[@name=\"telephone\"]")
	private WebElement telePhone;

	public WebElement telephone() {
		return telePhone;
	}

	@FindBy(xpath = "//*[@id=\"checkout-shipping-method-load\"]/table/tbody/tr[1]/td[1]")
	private WebElement flatRate;

	public WebElement flatRate() {
		return flatRate;
	}

	@FindBy(xpath = "//span[text()=\"Next\"]")
	private WebElement nextButton;

	public WebElement nextButton() {
		return nextButton;
	}

	@FindBy(xpath = "//span[text()=\"Place Order\"]")
	private WebElement placeOrder;

	public WebElement placeOrder() {
		return placeOrder;
	}

	@FindBy(xpath = "//span[@data-ui-id=\"page-title-wrapper\"]")
	private WebElement successMessage;

	public WebElement successMessage() {
		return successMessage;
	}

	@FindBy(xpath = "//input[@type=\"checkbox\"]")
	private WebElement billingAddress;

	public WebElement billingAddress() {
		return billingAddress;
	}

	@FindBy(xpath = "//img[contains(@alt, 'Loading')]")
	private WebElement loaderImg;

	public WebElement loaderImg() {
		return loaderImg;
	}

	@FindBy(xpath = "//div[@data-role=\"loader\"]")
	private WebElement dataLoader;

	public WebElement dataLoader() {
		return dataLoader;
	}

	@FindBy(xpath = "//img[@alt=\"Loading...\"]")
	private WebElement lloaderLast;

	public WebElement lloaderLast() {
		return lloaderLast;
	}

	@FindBy(xpath = "//dl[@class='item-options']//dt[text()='Size']/following-sibling::dd[1]")
	private WebElement getSizeInCart;

	

	

	public List<String> getColorInCart() {
		List<WebElement> color = driver
				.findElements(By.xpath("//dl[@class='item-options']//dt[text()='Color']/following-sibling::dd[1]"));
		List<String> actualColors = new ArrayList<>();
		for (WebElement c : color) {
			actualColors.add(c.getText().trim());
		}
		return actualColors;

	}
	
	public List<String> getSizeInCart() {
		List<WebElement> size = driver
				.findElements(By.xpath("//dl[@class='item-options']//dt[text()='Size']/following-sibling::dd[1]"));
		List<String> actualSize = new ArrayList<>();
		for (WebElement s : size) {
			actualSize.add(s.getText().trim());
		}
		return actualSize;

	}
	
	public List<WebElement> getCartItems(){
		List<WebElement> eleList=driver.findElements(By.xpath("//tbody[@class=\"cart item\"]"));
		return eleList;
	}
}
