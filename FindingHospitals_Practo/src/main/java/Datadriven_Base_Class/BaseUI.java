package Datadriven_Base_Class;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class BaseUI {

	public WebDriver driver;
	static public Properties prop;
	// public RemoteWebDriver driver;

	public void invokeBrowser(String browserNameKey) throws MalformedURLException {

		if (browserNameKey.equalsIgnoreCase("chrome")) {

			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "//Driver//chromedriver.exe");
			driver = new ChromeDriver();

			// cap=DesiredCapabilities.chrome();
			// ChromeOptions cap = new ChromeOptions();
			// cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,
			// UnexpectedAlertBehaviour.IGNORE);
			// driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),cap);

		} else if (browserNameKey.equalsIgnoreCase("firefox")) {

			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "//Driver//geckodriver.exe");
			driver = new FirefoxDriver();

			// cap=DesiredCapabilities.firefox();
			// DesiredCapabilities cap = DesiredCapabilities.firefox();
			// driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),cap);

		} else if (browserNameKey.equalsIgnoreCase("edge")) {
			// cap=DesiredCapabilities.edge();
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "//Drivers//chromedriver.exe");
			driver = new ChromeDriver();

		}

		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);

		if (prop == null) {
			prop = new Properties();
			FileInputStream file;
			try {
				 file = new
				 FileInputStream(System.getProperty("user.dir")+"\\Configure_Properties\\Element_Locators.properties");
				prop.load(file);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void getUrl(String websiteURL) {

		driver.get(prop.getProperty(websiteURL));
	}

	public void locateCitySearchBox() {
		// Highlighting the value
		WebElement ele = driver.findElement(By.xpath(prop.getProperty("selectCity")));
		// Create object of a JavascriptExecutor interface
		JavascriptExecutor js = (JavascriptExecutor) driver;
	    js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", ele);

	}

	public void clearText() {

		// clear value
		driver.findElement(By.xpath(prop.getProperty("selectCity"))).clear();
		
	}

	public void enterBangloreInSearchedField() throws InterruptedException {

		// Writing Bangalore in the search box
	    driver.findElement(By.xpath(prop.getProperty("selectCity"))).sendKeys("Bangalore");
		Thread.sleep(1000);
		// Down arrow function to select the required value
		driver.findElement(By.xpath(prop.getProperty("selectCity"))).sendKeys(Keys.ARROW_DOWN);
		Thread.sleep(1000);
		driver.findElement(By.xpath(prop.getProperty("selectCity"))).sendKeys(Keys.ARROW_DOWN);
		Thread.sleep(1000);
		driver.findElement(By.xpath(prop.getProperty("selectCity"))).sendKeys(Keys.ENTER);

	}

	public void locateHospitalField() {

		// Highlighting the value
		WebElement ele1 = driver.findElement(By.xpath(prop.getProperty("selectHospital")));
		// Create object of a JavascriptExecutor interface
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", ele1);

	}

	public void writeHospital() throws InterruptedException {

		// Writting Hospital in the search box
		driver.findElement(By.xpath(prop.getProperty("selectHospital")))
				.sendKeys("Hospital");
		Thread.sleep(1000);
		// Down arrow function to select the required value
		driver.findElement(By.xpath(prop.getProperty("selectHospital")))
				.sendKeys(Keys.ARROW_DOWN);
		Thread.sleep(1000);
		driver.findElement(By.xpath(prop.getProperty("selectHospital")))
				.sendKeys(Keys.ENTER);
		Thread.sleep(1000);
		
	}

	public void selectCheckBoxfor24X7() {

		// Check if the hospital is 24/7 open filter
		 driver.findElement(By.xpath(prop.getProperty("Checkbox_24x7"))).click();
		
	}

	public void clickOnApplyFilter() throws InterruptedException {

		 driver.findElement(By.xpath(prop.getProperty("applyFilter"))).click();
		 Thread.sleep(1000);
		 driver.findElement(By.xpath(prop.getProperty("applyFilter"))).click();
		 Thread.sleep(1000);

	}

	public void applyParkingFilter() {

		// if the hospital has parking filter
		 driver.findElement(By.xpath(prop.getProperty("applyParkingFilter"))).click();

	}

	public void storeDesiredHospitals() throws InterruptedException, IOException {

		// scroll function
		for (int j = 0; j < 5; j++) {
			JavascriptExecutor js2 = (JavascriptExecutor) driver;
			// scrolling to get more hotels
			js2.executeScript("window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(1000);
			
		}
         System.out.println("**************************************");
         System.out.println("Hospitals Name :");
         
		// list of hospital with name ,location and rating
		 List<WebElement> name=driver.findElements(By.xpath(prop.getProperty("Hos_Name")));
		 List<WebElement> location=driver.findElements(By.xpath(prop.getProperty("Hos_Location")));
	 	 List<WebElement> rating = driver.findElements(By.xpath(prop.getProperty("Hos_Rating")));
		@SuppressWarnings("resource")
		// Blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		// Create Excel Sheet
		XSSFSheet sheet = workbook.createSheet("Details");
		// Iterate over the Data
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("SNo.");
		row.createCell(1).setCellValue("Name");
		row.createCell(2).setCellValue("Location");
		row.createCell(3).setCellValue("Rating");

		
		// hospitals of Bangalore city with rating more than 3.5
		for (int i = 0, j = 0; i < rating.size(); i++) {
			if (!rating.get(i).getText().equals("3") && !rating.get(i).getText().equals("3.5")) {
				j++;
				row = sheet.createRow(j);
				row.createCell(0).setCellValue(j);
				System.out.println(name.get(i).getText());
				row.createCell(1).setCellValue(name.get(i).getText());
				row.createCell(2).setCellValue(location.get(i).getText());
				row.createCell(3).setCellValue(rating.get(i).getText());
			}
		}
		// Writing data in Excel Sheet
		FileOutputStream fos = new FileOutputStream(new File("Hostipals.xlsx"));
		workbook.write(fos);
		System.out.println("Excel File created");

	}

	public void locateDignosticPageLink() {

		WebElement a = driver.findElement(By.xpath(prop.getProperty("Dignostic_Text")));
		// Create object of a JavascriptExecutor interface
		JavascriptExecutor j = (JavascriptExecutor) driver;
		j.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", a);
     
	}

	public void clickDignosticLink() {

		// Clicking on Dignostic link
		driver.findElement(By.xpath(prop.getProperty("Dignostic_Text"))).click();
		
	}

	public void highlightCityBanglore() {
		
		// Highlighting the value bangalore
		 WebElement ele = driver.findElement(By.xpath(prop.getProperty("H_Bangalore")));
		// Create object of a JavascriptExecutor interface
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", ele);

	}

	public void highlightCityDelhi() {

		// Highlighting the value Delhi
	    WebElement ele1 = driver.findElement(By.xpath(prop.getProperty("H_Delhi")));
		// Create object of a JavascriptExecutor interface
		JavascriptExecutor js1 = (JavascriptExecutor) driver;
		js1.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", ele1);

	}

	public void highlightCityMumbai() {

		//Highlighting the value Mumbai
		WebElement ele2 = driver.findElement(By.xpath(prop.getProperty("H_Mumbai")));
        // Create object of a JavascriptExecutor interface
		JavascriptExecutor js2 = (JavascriptExecutor) driver;
		js2.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", ele2);

	}

	/*************************HightLighting Hydrabad City ***************************************/
	
	public void highlightCityHydrabad() {

		//Highlighting the value hydrabad
		WebElement ele3 = driver.findElement(By.xpath(prop.getProperty("H_Hydrabad")));
	    //Create object of a JavascriptExecutor interface
		JavascriptExecutor js3 = (JavascriptExecutor) driver;
		js3.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", ele3);

	}

	/*************************HightLighting Pune City ***************************************/
	
	public void highlightCityPune() {

		// Highlighting the value pune
		WebElement ele4 = driver.findElement(By.xpath(prop.getProperty("H_Pune")));
        // Create object of a JavascriptExecutor interface
		JavascriptExecutor js4 = (JavascriptExecutor) driver;
		js4.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", ele4);

	}

	/************************** Storing Top Cities Name *******************************************/
	
	public void storeAndDisplayTopCitiesName() throws IOException {

		// display all top cities
		List<WebElement> cities = driver.findElements(By.xpath(prop.getProperty("Top_Cities")));
		System.out.println("********************************************");
		System.out.println("There are " + cities.size() + " top cities");
		cities.size();

		XSSFWorkbook workbook = new XSSFWorkbook();
		// Create Excel Sheet
		XSSFSheet sheet = workbook.createSheet("Details");
		// Iterate over the Data
		Row row = sheet.createRow(0);
		row.createCell(0).setCellValue("SNo.");
		row.createCell(1).setCellValue("Top Cities");

		for (int i = 0; i < cities.size(); i++) {
			row = sheet.createRow(i + 1);
			row.createCell(0).setCellValue(i + 1);
			row.createCell(1).setCellValue(cities.get(i).getText());
			System.out.println(cities.get(i).getText());
		}

		// Write Down file on HardDisk
		FileOutputStream foss = new FileOutputStream(new File("Top_Cities_Name.xlsx"));
		workbook.write(foss);
		System.out.println("Excel file is being created Successfully!");
		cities.get(0).click();

	}
	

	/****************************Function to Click on Data Providers *******************************************/
	
	public void clickOnProviders() throws InterruptedException {
		// xPath for the providers text
		driver.findElement(By.xpath(prop.getProperty("Data_Providers")))
				.click();
		driver.findElement(By.xpath(prop.getProperty("Data_Providers")))
		.click();

		Thread.sleep(1000);

	}

	/****************************Function to Click on Corporate Wellness Link *******************************************/
	
	public void clickOnWellnessText() throws InterruptedException {

		// xPath for wellness text
		driver.findElement(By.xpath(prop.getProperty("Corporate_Wellness")))
				.click();
		Thread.sleep(1000);

	}

	/****************************Function to handle Tabs *****************************************************************/
	
	
	public void handleTabs() throws InterruptedException {

		Set<String> winHandles = driver.getWindowHandles();
		for (String wh : driver.getWindowHandles()) {
			driver.switchTo().window(wh);
		}

		Thread.sleep(2000);

	}

	
	public void enterValidNameError(String ss) throws InterruptedException, HeadlessException, AWTException, IOException {

		WebElement name = driver.findElement(By.xpath(prop.getProperty("submit_Form")));

		name.click();
		// Thread.sleep(40000);
		WebDriverWait wait = new WebDriverWait(driver, 1200);
		wait.until(ExpectedConditions.alertIsPresent());

		Alert a = driver.switchTo().alert();
			
		
		
		String text4 = driver.switchTo().alert().getText();
		Thread.sleep(1000);
		
		System.out.println(text4);
		
		
		
		 BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
	     ImageIO.write(image, "png", new File(System.getProperty("user.dir") + "\\Screenshots\\"  +ss+ ".png"));
	      
		
		a.accept();
		// Thread.sleep(1000);

	}

	public void screenshot(String SSname) throws IOException{
		File SSfileName = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(SSfileName, new File(System.getProperty("user.dir") + "\\Screenshots\\" + SSname + ".png"));
		
	}

	public void enterName(String Name) {

		// name
		driver.findElement(By.id(prop.getProperty("form_name_id"))).sendKeys("Rupesh");

	}

	public void enterOrganizationName(String OrgName) {

		// organizzation
		driver.findElement(By.xpath(prop.getProperty("Organization_Name_xpath"))).sendKeys(OrgName);

	}

	public void EnterEmail(String email) {

		// email
		driver.findElement(By.id(prop.getProperty("Locate_Email_Box"))).sendKeys(email);

	}

	public void EnterContactNumber(String ph) {

		// contact
		driver.findElement(By.id(prop.getProperty("contact_Texbox"))).sendKeys(ph);

	}

	public void EnterSize(String size) {

		// Size
		WebElement s = driver.findElement(By.id(prop.getProperty("org_Size_selectbox")));

		Select select = new Select(s);
		select.selectByVisibleText(size);

	}

	public void submitForm(String Invalid) throws InterruptedException, HeadlessException, AWTException, IOException {

		WebElement name = driver.findElement(By.xpath(prop.getProperty("submit_Form")));

		name.click();
		
		// Thread.sleep(40000);
		WebDriverWait wait = new WebDriverWait(driver, 1200);
		wait.until(ExpectedConditions.alertIsPresent());

		Alert a = driver.switchTo().alert();
				
		String text = driver.switchTo().alert().getText();
		Thread.sleep(1000);

		System.out.println(text);
		
	    BufferedImage img = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
	    ImageIO.write(img, "png", new File(System.getProperty("user.dir") + "\\Screenshots\\"  +Invalid+ ".png"));
	    
		
		a.accept();
		// Thread.sleep(1000);

	}

}
