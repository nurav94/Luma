package utils;

import org.testng.ITestListener;
import org.testng.ITestResult;

import base.BaseClass;

public class TestListener implements ITestListener{
	
	
	 public void onTestFailure(ITestResult result) {
		    BaseClass.getScreenshots();
		  }

	
}
