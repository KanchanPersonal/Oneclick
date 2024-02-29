package com.OneClick.tests;

import java.io.IOException;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.OneClick.core.DriverUtility;
import com.OneClick.pages.OneClickCar;

public class OneClickCarTest {

	@BeforeMethod(alwaysRun = true)
	@Parameters({ "browser" })
	public void launchBrowser(String browser) throws IOException, InterruptedException {
		DriverUtility.setUp(browser);

	}

	@Test(description = "Verify the List of Cars ", priority = 1, groups = { "smoke" })
	public void verifyListOfCars() {
		OneClickCar carsPage = new OneClickCar(DriverUtility.driver);
		// Get the lists of cars
		carsPage.listOfCars();
	}
}
