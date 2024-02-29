package com.OneClick.core;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.core.util.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.apache.commons.io.FileUtils;


public class SeleniumUtils {

	// Finding the elements - single, multiple
	// SendKeys
	// Click
	// Handling Alerts
	// handleAlert() -- Accept, Dismiss, get the text, enter the text
	// Waits
	// Handle the Mouse Actions
	// Current URL, Title
	// Handling Multiple browser tabs - not
	// Handling Frames - done
	// get text
	// Reusable and Generic methods used by all the pages
	// Scrolling - Scroll to an element, scroll down, scroll top - done
	// fileupload-using sendkeys??only when we have the tag name as input-use autoit
	// dropdown handling

	public WebElement getElement(String locatoryType, String locValue) {
		List<WebElement> list = getElements(locatoryType, locValue);
		if (list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	public List<WebElement> getElements(String locatorType, String locValue) {
		// return DriverUtility.driver.findElements(getByObject(locatoryType,
		// locValue));
		WebDriverWait wait = new WebDriverWait(DriverUtility.driver, Duration.ofSeconds(30));
		List<WebElement> elements = null;

		try {
			elements = waitForElement(getByObject(locatorType, locValue), "visibilityall", 30);
			if (elements.size() <= 1) {
				elements = waitForElement(getByObject(locatorType, locValue), "visibility", 30);
			}
		} catch (Exception e) {
			System.out.println("An exception occurred: " + e.getMessage());
		}

		return elements;

	}

	// reusable explicit wait conditions for comman used methods like
	// usability,clickable,alertis present,urlcontains

	public void clickOnElement(String locatoryType, String locValue) {
		getElement(locatoryType, locValue).click();
	}

	private By getByObject(String locatorType, String locatorValue) {
		By byObject = null;

		switch (locatorType) {
		case "id":
			byObject = By.id(locatorValue);
			break;
		case "name":
			byObject = By.name(locatorValue);
			break;
		case "className":
			byObject = By.className(locatorValue);
			break;
		case "xpath":
			byObject = By.xpath(locatorValue);
			break;
		case "linktext":
			byObject = By.linkText(locatorValue);
			break;
		case "partiallink":
			byObject = By.partialLinkText(locatorValue);
			break;
		case "cssSelector":
			byObject = By.cssSelector(locatorValue);
			break;
		case "tagname":
			byObject = By.tagName(locatorValue);
			break;
		default:
			System.out.println("Locator type not found.");
		}

		return byObject;
	}

	public void typeText(String locatorType, String locatorValue, String text) {
		WebElement element = getElement(locatorType, locatorValue);

		if (element != null) {
			element.clear(); // Optional if needed
			element.sendKeys(text);
		}
	}

	public void typeText(WebElement element, String text) {

		if (element != null) {
			element.clear(); // Optional if needed
			element.sendKeys(text);
		}
	}

	public String getAlert(String action) { // change name to HandleAlert
		WebDriverWait wait = new WebDriverWait(DriverUtility.driver, Duration.ofSeconds(30));
		wait.until(ExpectedConditions.alertIsPresent());

		Alert alert = DriverUtility.driver.switchTo().alert();

		if (action.equalsIgnoreCase("accept")) {
			alert.accept();
			return null;
		} else if (action.equals("dismiss")) {
			alert.dismiss();
			return null;
		} else if (action.equals("getText")) {
			String alertText = alert.getText();
			alert.accept();
			return alertText;

		}
		return null;
	}

	public String getText(String locatorType, String locatorValue) {
		List<WebElement> elements = getElements(locatorType, locatorValue);
		if (elements.size() > 0) {
			return elements.get(0).getText();
		} else {
			return null;
		}
	}

	public static void performMouseAction(String action, WebElement element) {
		switch (action) {
		case "click":
			DriverUtility.action.click(element).build().perform();
			break;
		case "rightClick": // Add this case for right-click
			DriverUtility.action.contextClick(element).build().perform();
			break;
		case "click_and_hold":
			DriverUtility.action.clickAndHold(element).build().perform();
			break;
		case "single_click":
			DriverUtility.action.click(element).build().perform();
			break;
		case "double_click":
			DriverUtility.action.doubleClick(element).build().perform();
			break;
		case "move_to_element":
			DriverUtility.action.moveToElement(element).build().perform();
			break;
		default:
			System.out.println("Invalid action specified");
		}
	}

	public List<WebElement> waitForElement(By locator, String waitType, int timeoutInSeconds) {
		WebDriverWait wait = new WebDriverWait(DriverUtility.driver, Duration.ofSeconds(timeoutInSeconds)); // Use
																											// Duration
																											// for
																											// timeout

		List<WebElement> element = new ArrayList<>();
		// wait.until(new Function<>{})

		// Comparator Interface - java collections framework
		// To compare the objects in the list -- List<Integers>, List<Employee>

		switch (waitType) {
		case "visibility":
			element.add(wait.until(ExpectedConditions.visibilityOfElementLocated(locator)));
			break;
		case "clickable":
			element.add(wait.until(ExpectedConditions.elementToBeClickable(locator)));
			break;
		case "presence":
			element.add(wait.until(ExpectedConditions.presenceOfElementLocated(locator)));
			break;
		case "visibilityall":
			element.addAll(wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator)));
			break;
		default:
			System.out.println("Invalid wait type specified");
		}

		return element;
	}

	public static String getCurrentURL() {
		return DriverUtility.driver.getCurrentUrl();
	}

	public static String getPageTitle() {
		return DriverUtility.driver.getTitle();
	}

	public String getCurrentWindowID() {
		return DriverUtility.driver.getWindowHandle();
	}

	public void scrollToElement(WebElement element) {
		// By object -->
		// Actions actions = new Actions(DriverUtility.driver);
		DriverUtility.action.scrollToElement(element).perform();
	}

	public void scrollToElementByAmountDown(int y, String browser) {
		if (browser.equalsIgnoreCase("chrome")) {
			DriverUtility.action.scrollByAmount(0, y).perform();
		} else {
			JavascriptExecutor js = (JavascriptExecutor) DriverUtility.driver;
			js.executeScript("window.scrollBy(0,y)", "");
		}
	}

	public void scrollToElementByAmountUp(int y, String browser)// y will be -ve
	{
		if (browser.equalsIgnoreCase("chrome")) {
			DriverUtility.action.scrollByAmount(0, y).perform();
		} else {
			JavascriptExecutor js = (JavascriptExecutor) DriverUtility.driver;
			js.executeScript("window.scrollBy(0,y)", "");
		}
	}
	// Scrolling down the page till the element is found
	// js.executeScript("arguments[0].scrollIntoView();", Element);//until visible
	// scroll

	public void switchToFrame(String frameIdentifier, String type) {// frameidentifier is locator of frame
		try {
			switch (type.toUpperCase()) {
			case "FRAME_ID_OR_NAME":
				DriverUtility.driver.switchTo().frame(frameIdentifier);
				break;
			case "FRAME_INDEX":
				int index = Integer.parseInt(frameIdentifier);
				DriverUtility.driver.switchTo().frame(index);
				break;
			case "FRAME_BY_ELEMENT":
				WebElement frameElement = DriverUtility.driver.findElement(By.xpath(frameIdentifier));
				DriverUtility.driver.switchTo().frame(frameElement);
				break;
			case "FRAME_BY_DEFAULT":
				DriverUtility.driver.switchTo().defaultContent();
				break;
			default:
				System.out.println("Unsupported frame identifier type.");
				break;
			}
		} catch (NumberFormatException e) {
			System.out.println("Invalid frame index: " + frameIdentifier);
		} catch (IllegalArgumentException e) {
			System.out.println("Invalid frame identifier type: " + type);
		} catch (Exception e) {
			// Handle exceptions, such as issues with switching to the frame
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public void switchToNewTabOrWindow(String windowType) throws InterruptedException {
		if (windowType.equalsIgnoreCase("tab")) {
			Thread.sleep(2000);
			DriverUtility.driver = DriverUtility.driver.switchTo().newWindow(WindowType.TAB);
			Thread.sleep(2000);
		} else if (windowType.equalsIgnoreCase("window")) {
			Thread.sleep(2000);
			DriverUtility.driver.switchTo().newWindow(WindowType.WINDOW);
			DriverUtility.driver.manage().window().maximize();
		} else {
			throw new IllegalArgumentException("Invalid window type: " + windowType);
		}
	}
	// comman method to click on home page links

	public void navigateTo(String linkname) {
		String currenturl = getCurrentURL();
		if (currenturl.contains("https://demoblaze.com/")) {
			clickOnElement("partiallink", linkname);
		} else {
			DriverUtility.log.error("Enter correct link name");
		}
	}

	public void gotoURL(String url) {
		DriverUtility.driver.get(url);
	}

	public void selectByVal(WebElement e, String Value) {

		Select e1 = new Select(DriverUtility.driver.findElement(By.name("country")));
		e1.deselectByValue(Value);
	}

	public void takeScreenshot(String filename) {
		// Cast WebDriver to firefox as full page screenshot is available in that only
		// Capture screenshot as File
		File src = ((FirefoxDriver)DriverUtility.driver).getFullPageScreenshotAs(OutputType.FILE);

		// Define the destination location for the screenshot
		String destination = System.getProperty("user.dir") + File.separator + "screenshots" + File.separator + filename + ".png";

		File dest = new File(destination);

		try {
			// Copy the screenshot to the desired location
			FileHandler.copy(src, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
