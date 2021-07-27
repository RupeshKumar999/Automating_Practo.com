package pageClasses;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import baseClass.pageBaseClass;

public class corporateWellnessPage extends pageBaseClass{
	
	public WebDriver driver;
	//Constructor
	public corporateWellnessPage(WebDriver driver) {
		this.driver = driver;
		
	}
	

	
	public void submitForm(String name, String organizationName, String email, String phone, String size) throws IOException {
		
		//Entering data in form
		
		//driver.findElement(By.id(prop.getProperty("name_id"))).sendKeys(name);
		//Highlighting the value
  		WebElement Nm = driver.findElement(By.id(prop.getProperty("name_id")));
  		
  		//Create object of a JavascriptExecutor interface
  		JavascriptExecutor js = (JavascriptExecutor) driver;
  	     // pass values based on css style. Yellow background color with solid red color border. 
  		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", Nm);
  		
		Nm.sendKeys(name);
		
		
		
		
		
		//driver.findElement(By.id(prop.getProperty("organisation_id"))).sendKeys(organizationName);
		
		WebElement orgNm = driver.findElement(By.id(prop.getProperty("organisation_id")));
		
		
	   js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", orgNm);
  		
		Nm.sendKeys(organizationName);
	
		
		//driver.findElement(By.id(prop.getProperty("email_id"))).sendKeys(email);
		
		WebElement mail= driver.findElement(By.id(prop.getProperty("email_id")));
		 js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", mail);
	  		mail.sendKeys(email);
		
	//	driver.findElement(By.id(prop.getProperty("phone_id"))).sendKeys(phone);
		
	  		WebElement ph = driver.findElement(By.id(prop.getProperty("phone_id")));
	  		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", ph);
	  		ph.sendKeys(phone);
		
	  		
	  		WebElement s = driver.findElement(By.id(prop.getProperty("size_id")));
	  		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", s);
	  		
			
		Select select = new Select(s);
		select.selectByVisibleText(size);
		
		//ScreenShot and Extent Report
		screenshot("FormFilled.png",driver);
		reportPass("Form filled");
		
		
		driver.findElement(By.xpath(prop.getProperty("submit_xpath"))).click();
		
		//Switching i-frame
		driver.switchTo().frame(2);
		
		//Explicit wait of 1 second
		WebDriverWait wait = new WebDriverWait(driver, 1);
		
		//Using this while loop because there is "Image CAPTCHA" in webSite which we have to Solve manually
		while(true) {
			//Handling alert
			try {
				if(wait.until(ExpectedConditions.alertIsPresent())!=null) {
					reportFail(driver.switchTo().alert().getText());
					System.out.println("****************************Alert********************************");
					System.out.println(driver.switchTo().alert().getText());
					driver.switchTo().alert().accept();
					break;
				}
			} catch (org.openqa.selenium.TimeoutException ex) {
				
				
			}
			//switch back iframe to default 
			driver.switchTo().defaultContent();
			if(driver.findElement(By.id("thankyou-section")).isDisplayed()) {
				reportPass("Form Submitted");
				screenshot("ThankYouPage.png",driver);
				System.out.println("Test Case Passed");
				break;
			}
			//switch to i-frame
			driver.switchTo().frame(2);
		}
		//Quit driver
		driver.quit();
	}
}
