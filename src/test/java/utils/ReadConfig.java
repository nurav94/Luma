package utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfig {

	Properties prop;

	public ReadConfig() {
		try {
			File configFileLoc = new File(
					System.getProperty("user.dir") + "\\src\\test\\java\\config\\config.properties");
			FileInputStream fis = new FileInputStream(configFileLoc);
			prop = new Properties();
			prop.load(fis);
		} catch (Exception e) {
			System.out.println("Exception is " + e.getMessage());
			e.printStackTrace();
		}
	}

	public String getBrowser() {
		String browser = prop.getProperty("selectBrowser");
		return browser;
	}

	public String getbaseUrl() {
		String url = prop.getProperty("baseUrl");
		return url;
	}

}
