package testcases;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import base.BaseClass;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import webelements.HomeElements;

public class FirstTest extends BaseClass {

	@Test(priority = 0)
	@Severity(SeverityLevel.NORMAL)
	@Description("Verifying the Title")
	public void verifyTitle() {
		Assert.assertEquals(driver.getTitle(), "Home Page");

	}

	@Test(priority = 1)
	@Severity(SeverityLevel.NORMAL)
	@Description("Checking whether the user can type and delete in the search bar")
	public void searchBarEditing() {
		HomeElements homePage = PageFactory.initElements(driver, HomeElements.class);
		homePage.search().sendKeys("women");
		Allure.addAttachment("Entering value into searchbox", "women");
		assertEquals(homePage.search().getAttribute("value"), "women");
		getScreenshots();
		homePage.search().clear();

	}

	@Test(priority = 2)
	@Severity(SeverityLevel.CRITICAL)
	@Description("Checking whether user can search using the search field")
	public void searchFunctionality() {
		HomeElements homePage = PageFactory.initElements(driver, HomeElements.class);
		homePage.search().sendKeys("man");
		Allure.addAttachment("Entering value into searchbox", "man");
		homePage.search().sendKeys(Keys.ENTER);
		Assert.assertTrue(driver.getCurrentUrl().contains("man"));

	}

	@Test(priority = 3)
	@Severity(SeverityLevel.MINOR)
	@Description("Checking if there are any broken images")
	public void checkingBrokenImages() {

		// Initialize the SoftAssert instance once before the loop
		SoftAssert softAssert = new SoftAssert();

		// Loop through each image element
		for (String images : imageSrc) {

			System.out.println("Checking image URL: " + images);

			// Check if the src attribute is not null or empty
			if (images != null && !images.isEmpty()) {
				try {
					// Create a URL object from the src attribute
					URL url = new URL(images);

					// Open a connection to the URL
					HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

					// Set the request method to "HEAD" to fetch only the headers
					httpURLConnection.setRequestMethod("HEAD");

					// Connect to the URL
					httpURLConnection.connect();

					// Get the response code
					int responseCode = httpURLConnection.getResponseCode();

					// If the response code is 400 or greater, the image is considered broken
					if (responseCode >= 400) {
						String brokenImageMessage = "Broken image: " + images + " - Response code: " + responseCode;
						System.out.println(brokenImageMessage);
						Allure.addAttachment("Broken image found", brokenImageMessage);
						softAssert.fail(brokenImageMessage); // Soft fail the assertion
					} else {
						String goodImageMessage = "Good image: " + images + " - Response code: " + responseCode;
						System.out.println(goodImageMessage);
					}

				} catch (IOException e) {
					// Handle any exceptions that occur during the connection process
					String errorMessage = "Error checking image: " + images;
					System.out.println(errorMessage);
					e.printStackTrace();
					softAssert.fail(errorMessage); // Soft fail the assertion
				}
			} else {
				// If the src attribute is null or empty, log this information
				String emptySrcMessage = "Image source is null or empty.";
				System.out.println(emptySrcMessage);
				softAssert.fail(emptySrcMessage); // Soft fail the assertion
			}
		}

		// Make sure to call assertAll() only after all images have been checked
		softAssert.assertAll(); // This will mark the test as failed if any soft assertion failed
	}

	@Test(priority = 4)
	@Severity(SeverityLevel.NORMAL)
	@Description("Checking if there are any broken links")
	public void checkingBrokenLinks() {
		SoftAssert softAssert = new SoftAssert();

		for (String href : BaseClass.links) {

			// Check if the href attribute is not null and not empty
			if (href != null && !href.isEmpty()) {
				System.out.println(href);

				// Allure.addAttachment("Checking for Broken URL: ", href);

				try {
					// Create a URL object from the href attribute
					URL url = new URL(href);

					// Open a connection to the URL
					HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

					// Set the request method to "HEAD" to fetch only the headers
					httpURLConnection.setRequestMethod("HEAD");

					// Connect to the URL
					httpURLConnection.connect();

					// Get the response code
					int responseCode = httpURLConnection.getResponseCode();

					// If the response code is 400 or greater, the link is considered broken
					if (responseCode >= 400) {
						String brokenLinkMessage = "Broken Link: " + href + " - Response code: " + responseCode;
						System.out.println(brokenLinkMessage);
						Allure.addAttachment("Broken Link found", brokenLinkMessage);
						softAssert.fail(brokenLinkMessage);

					} else {
						String validLinkMessage = "Valid Link: " + href + " - Response code: " + responseCode;
						System.out.println(validLinkMessage);
					}

				} catch (IOException e) {
					// Handle any exceptions that occur during the connection process
					System.out.println("Error checking Link: " + href);
					e.printStackTrace();
					softAssert.fail("Error checking Link: " + href);
				}
			} else {
				// Log if the href is null or empty
				System.out.println("Link source is null or empty.");
				softAssert.fail("Link source is null or empty.");
			}

		}
		// Make sure to execute the assertions after each iteration
		softAssert.assertAll();
	}

	@Test(priority = 5)
	@Severity(SeverityLevel.CRITICAL)
	@Description("Check whether user can add items to the cart")
	public void addItemsToCart() throws InterruptedException {
		HomeElements homePage = PageFactory.initElements(driver, HomeElements.class);
		SoftAssert softAssert = new SoftAssert();
		actions().moveToElement(homePage.men()).build().perform();
		actions().moveToElement(homePage.topsMen()).build().perform();
		homePage.menJacket().click();

		// clicking the first availabel jacket
		driver.findElement(By.xpath("//img[@class=\"product-image-photo\"]")).click();
		// checkingBrokenImages();
		homePage.blackColor().click();
		homePage.xL().click();
		homePage.addToCart().click();
		Thread.sleep(2000);
		Allure.addAttachment("Log: ", "An Item Added to the cart.");
		if (homePage.getCartCount() > 0) {
			Allure.addAttachment("Number of Items added to your cart: ", "" + homePage.getCartCount());
			Allure.addAttachment("Status message of cart: ", homePage.messageBox().getText());
			getScreenshots();
		} else {
			softAssert.fail("Items not added to cart");
		}

		// clicking the first availabel jacket

	}

	@Test(priority = 6)
	@Severity(SeverityLevel.CRITICAL)
	@Description("Boundary Value Analysis for Item Quantity Field. \n\1. Check Whether we can edit the items in cart.\n\2. Check the cart value with 0.\n\3. Check the maximum value of the cart")

	public void editCart() throws InterruptedException {
		HomeElements homePage = PageFactory.initElements(driver, HomeElements.class);
		SoftAssert softAssert = new SoftAssert();
		homePage.shoppingCart().click();
		homePage.quantityInputField().clear();
		homePage.quantityInputField().sendKeys("0");
		homePage.updateCart().click();
		if (homePage.quantityError().getText().equals("Please enter a number greater than 0 in this field.")) {
			System.out.println("system does not allows 0 as quantity");
			getScreenshots();
			Allure.addAttachment("Validation Message: ", homePage.quantityError().getText());
		} else {
			softAssert.fail();
			Allure.addAttachment("Validation Message: ", homePage.quantityError().getText());
		}
		homePage.quantityInputField().clear();
		Allure.addAttachment("Updating Cart Quantity to: ", "9800");
		homePage.quantityInputField().sendKeys("9800");
		homePage.updateCart().click();
		fluentWait(Duration.ofSeconds(10), Duration.ofNanos(1))
				.until(ExpectedConditions.visibilityOf(homePage.alertText()));
		Allure.addAttachment("Validation Message: ", homePage.alertText().getText());
		getScreenshots();
		homePage.alertOkButton().click();

		homePage.quantityInputField().clear();
		Allure.addAttachment("Updating Cart Quantity to: ", "");
		homePage.updateCart().click();
		Allure.addAttachment("Validation Message: ", homePage.quantityError().getText());
		getScreenshots();

		homePage.quantityInputField().clear();
		Allure.addAttachment("Updating Cart Quantity to: ", "100000");
		homePage.quantityInputField().sendKeys("100000");
		homePage.updateCart().click();
		fluentWait(Duration.ofSeconds(10), Duration.ofNanos(1))
				.until(ExpectedConditions.visibilityOf(homePage.alertText()));
		Allure.addAttachment("Validation Message: ", homePage.alertText().getText());
		getScreenshots();
		homePage.alertOkButton().click();

		softAssert.assertAll();
		Thread.sleep(5000);

	}

	@Test(priority = 7)
	@Severity(SeverityLevel.NORMAL)
	@Description("Checking whether user can remove the added item from the cart")
	public void removeItemFromCart() {
		HomeElements homePage = PageFactory.initElements(driver, HomeElements.class);
		SoftAssert softAssert = new SoftAssert();
		homePage.quantityInputField().clear();
		homePage.viewCart().click();
		homePage.viewEditCart().click();
		homePage.removeItem().click();
		if (homePage.getCartCount() > 0) {
			getScreenshots();
			Allure.addAttachment("Item removed, Number of Items in the cart: ", "" + homePage.getCartCount());
		} else {
			Allure.addAttachment("Item not removed, Number of Items in the cart: ", "" + homePage.getCartCount());
			softAssert.fail("Items Not Removed from Cart");

		}

	}

	@DataProvider(name = "productData")
	public Object[][] getProductData() {
		return new Object[][] { { "XS", "Black" }, { "S", "Blue" }, { "M", "Orange" }, { "L", "Black" },
				{ "XL", "Blue" } };
	}

	@Test(priority = 8)
	@Severity(SeverityLevel.NORMAL)
	@Description("All Pair Testing - Add items to the cart with different size and color combinations")
	public void allPairTesting() throws InterruptedException {
		HomeElements homePage = PageFactory.initElements(driver, HomeElements.class);
		redirecting(homePage);
		Object[][] data = getProductData(); // Calling the DataProvider manually

		for (Object[] row : data) {
			String size = (String) row[0];
			String color = (String) row[1];

			performAddToCartTest(size, color, homePage);
		}

		fluentWait(Duration.ofSeconds(15), Duration.ofMillis(1))
				.until(ExpectedConditions.invisibilityOf(homePage.cartLoader()));

		// Navigate to the "View and Edit Cart" page
		homePage.viewCart().click();
		homePage.viewEditCart().click();

		// Verify the cart data for each item using the DataProvider
		verifyCartDataWithoutOrder(data, homePage);

		Allure.addAttachment("Number of Items in the cart: ", "" + homePage.getCartCount());
		Allure.addAttachment("Number of Items added to your cart: ", "" + homePage.getCartCount());
	}

	@Step("Redirecting to Men's Jacket Page")
	public void redirecting(HomeElements homePage) {

		actions().moveToElement(homePage.men()).build().perform();
		actions().moveToElement(homePage.topsMen()).build().perform();
		homePage.menJacket().click();
		driver.findElement(By.xpath("//img[@class=\"product-image-photo\"]")).click();

	}

	@Step("Adding item to cart with Size: {0} and Color: {1}")
	public void performAddToCartTest(String size, String color, HomeElements homePage) throws InterruptedException {

		// Select size and color
		homePage.selectSize(size).click();
		homePage.selectColor(color).click();
		// Wait until the "Add to Cart" button returns to its original state
		explictWait(Duration.ofSeconds(10))
				.until(ExpectedConditions.textToBePresentInElement(homePage.addToCart(), "Add to Cart"));
		// Click on Add to Cart button
		homePage.addToCart().click();

		// Attach details to Allure report
		Allure.addAttachment("Added item to cart", "Size: " + size + ", Color: " + color);
		Allure.addAttachment("Status message of cart: ", homePage.messageBox().getText());
		getScreenshots();
	}

	public void verifyCartDataWithoutOrder(Object[][] expectedData, HomeElements homePage) {
		// Step 1: Extract expected size and color pairs from DataProvider
		Set<String> expectedCombinations = new HashSet<>();
		for (Object[] row : expectedData) {
			String size = (String) row[0];
			String color = (String) row[1];
			expectedCombinations.add(size + ":" + color);
		}

		// Step 2: Extract actual size and color pairs from the cart
		List<String> actualSizes = homePage.getSizeInCart();
		List<String> actualColors = homePage.getColorInCart();
		Set<String> actualCombinations = new HashSet<>();

		for (int i = 0; i < actualSizes.size(); i++) {
			String size = actualSizes.get(i).trim();
			String color = actualColors.get(i).trim();
			actualCombinations.add(size + ":" + color);
		}

		// Step 3: Assert that all expected combinations are present in the actual
		// combinations
		Assert.assertTrue(actualCombinations.containsAll(expectedCombinations),
				"Cart data does not match the expected size and color combinations");

		// Step 4: Attach comparison results to Allure report
		Allure.addAttachment("Expected Combinations", expectedCombinations.toString());
		Allure.addAttachment("Actual Combinations from Cart", actualCombinations.toString());
	}

	@Test(priority = 9)
	public void verifyProductImageColorAgainstSelectedColor() {
		HomeElements homePage = PageFactory.initElements(driver, HomeElements.class);
		verifyCartItemColor(homePage);
	}

	public void verifyCartItemColor(HomeElements homePage) {
	    List<WebElement> cartItems = homePage.getCartItems(); // Assume this returns a list of cart item elements
	    SoftAssert softAssert = new SoftAssert();

	    for (WebElement item : cartItems) {
	        try {
	            // Extract color from the <dd> element
	            String colorText = item.findElement(By.xpath(".//dt[text()='Color']/following-sibling::dd")).getText().trim().toLowerCase();

	            // Extract image URL from the <img> tag
	            String imageUrl = item.findElement(By.xpath(".//img[@class='product-image-photo']")).getAttribute("src").toLowerCase();

	            // Improve matching: Check if image URL contains the color or a substring match
	            boolean isColorMatching = imageUrl.contains(colorText);

	            // Assert the match and log the results
	            softAssert.assertTrue(isColorMatching,
	                "Color in cart (" + colorText + ") does not match or is not contained in image URL (" + imageUrl + ")");

	            // Log comparison results to Allure
	            Allure.addAttachment("Color in Cart Item", colorText);
	            Allure.addAttachment("Color in Image URL", imageUrl);

	        } catch (NoSuchElementException e) {
	            softAssert.fail("Element not found in cart item: " + e.getMessage());
	        }
	    }

	    // Report all collected assertions
	    softAssert.assertAll();
	}



	
	@Test(priority = 10)
	@Severity(SeverityLevel.BLOCKER)
	@Description("Check whether the Total amount is calculated correctly")
	public void checkTheTotalAmount() {

		HomeElements homePage = PageFactory.initElements(driver, HomeElements.class);
		homePage.viewCart().click();
		homePage.viewEditCart().click();
		Assert.assertEquals(homePage.addAllAmountInCart(), homePage.subTotal());
		Assert.assertEquals((homePage.addAllAmountInCart() + homePage.tax()) - homePage.discount(),
				homePage.orderTotal());
		Allure.addAttachment("Total Calculated Amount: ",
				"" + ((homePage.addAllAmountInCart() + homePage.tax()) - homePage.discount()));
		getScreenshots();

	}

	@Test(priority = 11)
	@Severity(SeverityLevel.MINOR)
	@Description("Refreshing the cart and checking whether the added items are still available")
	public void refreshingCart() {

		HomeElements homePage = PageFactory.initElements(driver, HomeElements.class);

		// saving the cartcount into the variable cartCount
		int cartCount = homePage.getCartCount();
		Allure.addAttachment("Cart Count before refreshing the page", "" + cartCount);
		driver.navigate().refresh();
		// verifying the cartcount remains the same after refreshing the page
		fluentWait(Duration.ofSeconds(15), Duration.ofMillis(1))
				.until(ExpectedConditions.invisibilityOf(homePage.cartLoader()));
		Assert.assertEquals(cartCount, homePage.getCartCount());
		Allure.addAttachment("Cart Count remains the same", "" + homePage.getCartCount());
		getScreenshots();

	}

	@Test(priority = 12)
	@Severity(SeverityLevel.BLOCKER)
	@Description("Checking whether the user can checkout the items in the cart")
	public void checkoutItemsInTheCart() throws InterruptedException {

		HomeElements homePage = PageFactory.initElements(driver, HomeElements.class);
		homePage.viewCart().click();
		homePage.viewEditCart().click();
		homePage.proceedToCheckOut().click();
		fluentWait(Duration.ofSeconds(15), Duration.ofNanos(1))
				.until(ExpectedConditions.invisibilityOf(homePage.lloaderLast()));
		homePage.emailId().sendKeys("arunaproject@gmail.com");
		homePage.firstName().sendKeys("Aruna");
		homePage.lastName().sendKeys("L");
		homePage.streetName().sendKeys("The Street");
		Select select = new Select(homePage.stateName());
		select.selectByIndex(1);
		homePage.cityName().sendKeys("Alabama");
		homePage.postCode().sendKeys("35762");
		homePage.telephone().sendKeys("93213333");
		homePage.flatRate().click();
		homePage.nextButton().click();
		fluentWait(Duration.ofSeconds(15), Duration.ofNanos(1))
				.until(ExpectedConditions.invisibilityOf(homePage.lloaderLast()));
		homePage.placeOrder().click();
		fluentWait(Duration.ofSeconds(15), Duration.ofNanos(1))
				.until(ExpectedConditions.invisibilityOf(homePage.lloaderLast()));
		Assert.assertEquals(homePage.successMessage().getText(), "Thank you for your purchase!");
		Allure.addAttachment("Log", homePage.successMessage().getText());
		getScreenshots();

	}

}
