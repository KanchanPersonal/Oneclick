package com.OneClick.core;

import java.io.IOException;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;

public class DriverUtility {
    public static WebDriver driver;
    public static Logger log = LogManager.getLogger();
    public static ConfigFileReader config= null;
    public static Actions action;
    public static void setUp(String browser) throws IOException {
    	
    	config = new ConfigFileReader();
    	

        if (browser == null) {
            browser = "chrome"; // Default to Chrome if no browser is specified
        }

        if (browser.equalsIgnoreCase("chrome")) {
        	 ChromeOptions options = new ChromeOptions();
          //  options.addArguments("--incognito");
             driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else if (browser.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        } else if (browser.equalsIgnoreCase("safari")) {
            driver = new SafariDriver();
        }

        if (driver != null) {
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
            driver.get(config.getApplicationUrl());
            
            action = new Actions(driver);

        }
    }
    	
  
    }




