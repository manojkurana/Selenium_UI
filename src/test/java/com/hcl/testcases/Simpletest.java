package com.hcl.testcases;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.hcl.pages.Homepage;
import com.hcl.testbaseclass.Testbase;
import com.hcl.utility.Utility;

public class Simpletest extends Testbase {

	public Simpletest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	Homepage hp;
	
	@BeforeTest
	public void setup()
	{
		initialize();
		
	}

	@Test
	public void simpletest()
	{
		
		System.out.println("Hi");
	}
	
	@Test
	public void test1()
	{
		hp=new Homepage();
		driver.get(repo.getProperty("url"));
		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);
		//hp.selectdivdropvalue(hp.div_drop,repo.getProperty("divdropvalue") );
		//hp.typeTextbox(hp.txtbox, repo.getProperty("value"));
		//hp.selectdropvalue(hp.drop_size, repo.getProperty("size"));
		hp.btn_click(hp.btn_cart);
		driver.manage().timeouts().implicitlyWait(5000, TimeUnit.SECONDS);
		//Assert.assertEquals(true, false);
	}

}
