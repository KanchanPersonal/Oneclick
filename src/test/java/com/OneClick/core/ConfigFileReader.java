package com.OneClick.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {

	public static Properties prop = null;

	public ConfigFileReader() throws IOException {
		// Get the user.dir system environment variable
		String projectPath = System.getProperty("user.dir");
		System.out.println("path = " + projectPath);
		FileInputStream fp = new FileInputStream(projectPath + "/configs/Configuration.properties");
		prop = new Properties();
		prop.load(fp);

	}

	public String getApplicationUrl() throws IOException {
		return prop.getProperty("url");

	}

}



