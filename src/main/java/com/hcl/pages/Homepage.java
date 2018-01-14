package com.hcl.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import com.hcl.testbaseclass.Testbase;

public class Homepage extends Testbase{

	public Homepage()
	{
		initilizeallelements();
	}

	@FindBy(xpath=".//*[@id='container']/shop-tab[1]/shop-ripple-container/a")
	WebElement link_mensoutwear;

	@FindBy(xpath=".//*[@id='sizeSelect']")
	public WebElement drop_size;


	@FindBy(name="userName")
	public WebElement txtbox;

	@FindBy(id="menu1")
	public WebElement div_drop;

	@FindBy(xpath="//*[text()[contains(.,'Add to Cart')]]")
	public WebElement btn_cart;
	
	public void selectdropvalue(WebElement element, String value)
	{
		Select drop_value=new Select(element);
		drop_value.selectByVisibleText(value);
	}

	public void typeTextbox(WebElement txtbox, String value)
	{
		txtbox.sendKeys("value");
	}

	public void selectdivdropvalue(WebElement element, String value)
	{

		
		element.click();
		List<WebElement> list = driver.findElements(By.xpath("//ul[@class='dropdown-menu']//li/a"));
		for (WebElement ele : list)
		{
			System.out.println("Values " + ele.getAttribute("value"));
			if (ele.getAttribute("innerHTML").contains("CSS")) {
				ele.click();
				break;
			}

		}
	}
	
	public void btn_click(WebElement element)
	{
		element.click();
	}


}
