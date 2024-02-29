package com.OneClick.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.OneClick.core.DriverUtility;

public class OneClickCar extends com.OneClick.core.SeleniumUtils {

	public OneClickCar(WebDriver driver) {
		// TODO Auto-generated constructor stub
	}

	public List<List<String>> listOfCars() {
		takeScreenshot("HomeScreen");
		clickOnElement("id", "ocd_view_Allcars");
		List<WebElement> cars = getElements("cssSelector", "h3.thumb-title");
		takeScreenshot("Listofcars");
		List<String> carsLessThanThreeChars = new ArrayList<>();
		List<String> carsGreaterThanThreeChars = new ArrayList<>();

		for (WebElement car : cars) {
			String carName = car.getText().trim();
			if (carName.length() < 3) {
				carsLessThanThreeChars.add(carName);
			} else {
				carsGreaterThanThreeChars.add(carName);
			}
		}
		// Log the count of cars for each category
		DriverUtility.log.info("Number of cars with less than three characters: " + carsLessThanThreeChars.size());
		DriverUtility.log.info("Number of cars with three or more characters: " + carsGreaterThanThreeChars.size());

		// Log the cars for each category
		DriverUtility.log.info("Cars with less than three characters:");
		for (String car : carsLessThanThreeChars) {
			DriverUtility.log.info(car);
		}

		DriverUtility.log.info("Cars with three or more characters:");
		for (String car : carsGreaterThanThreeChars) {
			DriverUtility.log.info(car);
		}

		// Return both lists in a single list
		List<List<String>> result = new ArrayList<>();
		result.add(carsLessThanThreeChars);
		result.add(carsGreaterThanThreeChars);
		return result;
	}
}
