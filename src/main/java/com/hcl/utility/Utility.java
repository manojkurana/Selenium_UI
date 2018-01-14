package com.hcl.utility;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import com.hcl.testbaseclass.Testbase;

public class Utility extends Testbase {
	
	public static void openurl()
	{
		String url=repo.getProperty("url");
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(4000,TimeUnit.SECONDS);
	}
	
	


}
