package Testclass;

import java.awt.AWTException;
import java.awt.HeadlessException;
import java.io.IOException;
import java.util.List;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import Datadriven_Base_Class.BaseUI;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.TestNG;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;



public class Testing  extends BaseUI{

	 ExtentHtmlReporter htmlReporter;
	 ExtentReports extent;
	 ExtentTest logger;
	 public static String[] data=new String[7];
	
	 
	 @BeforeTest
	 public void startReport(){
	 
	 htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") +"//Extent_Report//ExtentReport.html");
	 extent = new ExtentReports ();
	 extent.attachReporter(htmlReporter);
	 extent.setSystemInfo("Host Name", "SoftwareTestingMaterial");
	 extent.setSystemInfo("Environment", "Automation Testing");
	 extent.setSystemInfo("User Name", "Rajkumar SM");
	 
	 htmlReporter.config().setDocumentTitle("Title of the Report Comes here");
	 htmlReporter.config().setReportName("Name of the Report Comes here");
	 htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
	 htmlReporter.config().setTheme(Theme.STANDARD);
	 
	 
	 }
	  

	   //Function to read from Excel File
	   @BeforeTest
	   public String[] getExcelData () throws IOException { //Reads data from the excel file
		
		   data  = ReadExcelData.ReadExcelFile.getData(7, "ReadExcelData", "ReadData");
			return data;
		}  
 
	 
	 @AfterMethod
	 public void getResult(ITestResult result){
	 if(result.getStatus() == ITestResult.FAILURE){
	 logger.log(Status.FAIL, "Test Case Failed is "+result.getName());
	 //MarkupHelper is used to display the output in different colors
	 logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
	 logger.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable() + " - Test Case Failed", ExtentColor.RED));
	 }else if(result.getStatus() == ITestResult.SKIP){
	 logger.log(Status.SKIP, "Test Case Skipped is "+result.getName());
	 logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE)); 
	
	 }
	 }
	
	 
	@Test(priority=0,groups="smoke")
	public void validateURL() throws IOException {
		
		logger = extent.createTest("Login To Practo Site"); 
		
		invokeBrowser("chrome");
		getUrl("websiteURL");
		logger.log(Status.INFO, "Comparing Actual Vs Expected Url");	
		String Actual_Url=prop.getProperty("websiteURL");
		String Expected_Url =driver.getCurrentUrl();
		Assert.assertEquals(Actual_Url, Expected_Url);
		logger.log(Status.INFO, " Actual Url is Equal to Expected Url");	
		screenshot("HomePage");
		logger.addScreenCaptureFromPath(System.getProperty("user.dir") + "\\Screenshots\\HomePage.png");	
		logger.log(Status.PASS, "Test Case Passed");
		
	}
	
	
	@Test(priority=1,groups="smoke")
	public void EnterLocationInSearchBox() throws InterruptedException, IOException {
		
		logger = extent.createTest("Seaching the Desired Location");
		
		logger.log(Status.INFO, "Locating Location Field");
		locateCitySearchBox();
		logger.log(Status.INFO, "Clearing the present text");
		clearText();
		logger.log(Status.INFO, "Entering Banglore in the SearchBox");
		enterBangloreInSearchedField();
		logger.log(Status.INFO, "Comparing the Entered Text");	
		//Assertion
		String Text = driver.findElement(By.xpath(prop.getProperty("selectCity"))).getAttribute("value");
        Assert.assertEquals(Text, "Bangalore");
		screenshot("Location_Entered"); 
		logger.addScreenCaptureFromPath(System.getProperty("user.dir") + "\\Screenshots\\Location_Entered.png");	
		logger.log(Status.PASS, "Test Case Passed");	
		
	}
	

	@Test(priority=2,groups="smoke")
	public void EnterHospital() throws InterruptedException, IOException {
		
		logger = extent.createTest("Seaching For Hospital");
		
		logger.log(Status.INFO, "Locating Hospital Search Box");
		locateHospitalField();
		logger.log(Status.INFO, "Entering Hospital in the SearchBox");
		writeHospital();
	    String Text = driver.findElement(By.xpath(prop.getProperty("selectHospital"))).getAttribute("value");
	    Text.toLowerCase();
        Assert.assertEquals(Text,"hospital");
	    logger.log(Status.INFO, "Comparing the Entered Text");	
		screenshot("Hospital_Entered");
		logger.addScreenCaptureFromPath(System.getProperty("user.dir") + "\\Screenshots\\Hospital_Entered.png");
		logger.log(Status.PASS, "Test Case Passed");	
		
	}
	 
	
	@Test(priority=3,groups="smoke")
	public void Validating24X7CheckBox() throws InterruptedException, IOException {

		logger = extent.createTest("Clicking on 24X7 Checkbox");
		
		logger.log(Status.INFO, "Locating 24X7 CheckBox");
		selectCheckBoxfor24X7();
	    Thread.sleep(2000);
	    logger.log(Status.INFO, "Verifying Checkbox is selected");
	    //WebElement checkbox = driver.findElement(By.xpath(prop.getProperty("Checkbox_24x7")));
	    //Assert.assertTrue(checkbox.isSelected());
	    screenshot("Filter_24X7");
		logger.addScreenCaptureFromPath(System.getProperty("user.dir") + "\\Screenshots\\Filter_24X7.png");	
		logger.log(Status.PASS, "Test Case Passed");
		
	}
	
	
	
	@Test(priority=4,groups="smoke")
	public void ValidateParkingFilter() throws InterruptedException, IOException {
		
		logger = extent.createTest("Clicking on Parking Filter");
		
		logger.log(Status.INFO, "Clicking on Apply Filter");
		Thread.sleep(1000);
	    driver.findElement(By.xpath(prop.getProperty("applyFilter"))).click();
		clickOnApplyFilter();
		logger.log(Status.INFO, "Applying Parking Filter");
		applyParkingFilter();
		Thread.sleep(1000);
	    logger.log(Status.INFO, "Verifying Parking Filter is selected");	
	    //WebElement element = driver.findElement(By.xpath("//*[@data-qa-id='Open_24X7_checkbox']"));
	    //Assert.assertTrue(element.isSelected()); 
	    screenshot("Filter_Parking"); 
		logger.addScreenCaptureFromPath(System.getProperty("user.dir") + "\\Screenshots\\Filter_Parking.png");	
		logger.log(Status.PASS, "Test Case Passed");
		
	}
	
	
	@Test(priority=5,groups="smoke")
	public void ValidateStoreDesiredHospitals() throws InterruptedException, IOException {
		
        logger = extent.createTest("Storing the List of Desired Hospitals");
        
		logger.log(Status.INFO, "Locating all the Hospitals with more then 3.5 Rating");
		logger.log(Status.INFO, "Printing the Hospitals Name on Console and Excel Sheet");
		storeDesiredHospitals();
		screenshot("Storing_Hospitals"); 
	    logger.addScreenCaptureFromPath(System.getProperty("user.dir") + "\\Screenshots\\Storing_Hospitals.png");
		logger.log(Status.PASS, "Test Case Passed");
		driver.navigate().refresh();
		
	}
	
	
	@Test(priority=6,groups="smoke")
	public void ValidatingDignosticPage() throws IOException {
		
        logger = extent.createTest("Dignostic Page");
        
		logger.log(Status.INFO, "Locating the Dignostic Page button");
		driver.navigate().refresh();
		locateDignosticPageLink();
		logger.log(Status.INFO, "Clicking on the DignosticPage button");
		clickDignosticLink();
		String s = driver.findElement(By.xpath(prop.getProperty("City_Text"))).getText();
		Assert.assertEquals(s,"TOP CITIES");
		screenshot("Top_Cities"); 
		logger.addScreenCaptureFromPath(System.getProperty("user.dir") + "\\Screenshots\\Top_Cities.png");	
		logger.log(Status.PASS, "Test Case Passed");
			
	}
	
	@Test(priority=7,groups="smoke")
	public void HighlightTopCities() throws InterruptedException, IOException {
		
		logger = extent.createTest("HighLighting Top Cities");
		
		logger.log(Status.INFO, "Hightlighting  Banglore city");
		highlightCityBanglore();
		Thread.sleep(2000);
		logger.log(Status.INFO, "Hightlighting Delhi city");	
		highlightCityDelhi();
		Thread.sleep(2000);
		logger.log(Status.INFO, "Hightlighting Mumbai city");
		highlightCityMumbai();
		Thread.sleep(2000);
		logger.log(Status.INFO, "Hightlighting Hydrabad city");
		highlightCityHydrabad();
		Thread.sleep(2000);
		logger.log(Status.INFO, "Hightlighting Pune city");
		highlightCityPune();
		Thread.sleep(2000);
		screenshot("Highlighted _Top_Cities");
		logger.addScreenCaptureFromPath(System.getProperty("user.dir") + "\\Screenshots\\Highlighted _Top_Cities.png");	
		logger.log(Status.PASS, "Test Case Passed");
		
		
	}
	
	
	@Test(priority=8,groups="smoke")
	public void storingTopCities() throws IOException {
		
	    logger = extent.createTest("Storing Top Cities Names");
	    
	    logger.log(Status.INFO, "Locating Names of Top Cities");
		logger.log(Status.INFO, "Storing on the Excel File And Display of the Console");
		storeAndDisplayTopCitiesName();		
		logger.log(Status.PASS, "Test Case Passed");
	
	}	
	 
	
	@Test(priority=9,groups="smoke")
	public void openingCorporatePage() throws InterruptedException, IOException {
		
		 logger = extent.createTest(" Data Providers Link");	
		 
		 getUrl("websiteURL");
	     logger.log(Status.INFO, "Clicking on Data Providers");
		 clickOnProviders();
		 logger.log(Status.INFO, "Clicking on Wellness Link");
		 clickOnWellnessText();
		 logger.log(Status.INFO, "Handling the new Tab");
	     handleTabs();
	     Assert.assertEquals(prop.getProperty("Corporate_page_Title"),driver.getCurrentUrl());
	     screenshot("Corporte_Wellness_Form"); 
	     logger.addScreenCaptureFromPath(System.getProperty("user.dir") + "\\Screenshots\\Corporate_Wellness_Form.png");		
		 logger.log(Status.PASS, "Test Case Passed");	
			 
	}
	
	
     @Test(priority=10,groups="smoke")
	public void validateName() throws IOException, InterruptedException, HeadlessException, AWTException {
		

		 logger = extent.createTest("Submitting without Entering Data");
		 
		 logger.log(Status.INFO, "Submitting Form Without Entering Data");
    	 enterValidNameError("Enter_Name");
		 logger.addScreenCaptureFromPath(System.getProperty("user.dir") + "\\Screenshots\\Enter_Name.png");	
		 logger.log(Status.PASS, "Test Case Failed");

     }
	
     
	@Test(priority=11,groups="smoke")
	public void validatingWrongEmail() throws InterruptedException, IOException, HeadlessException, AWTException {
		
		logger = extent.createTest("Register with Wrong Email");
		
		 logger.log(Status.INFO, "Enter the Name");
		 enterName(data[0]);
		 logger.log(Status.INFO, "Enter the Organization Name");
		 enterOrganizationName(data[1]);
		 logger.log(Status.INFO, "Enter the Email");
		 EnterEmail(data[2]);
		 logger.log(Status.INFO, "Enter the Contact Number");
		 EnterContactNumber(data[3].replace("\"", ""));
		 logger.log(Status.INFO, "Choose the Size");
		 EnterSize(data[4]);
		 submitForm("Invalid_Email");     
		 logger.addScreenCaptureFromPath(System.getProperty("user.dir") + "\\Screenshots\\Invalid_Email.png");
		 logger.log(Status.PASS, "Test Case Failed");

	}
	
	
	@Test(priority=12,groups="smoke")
	public void validatingPhoneNumber() throws InterruptedException, IOException, HeadlessException, AWTException {
		
		
		logger = extent.createTest("Register with Wrong Email");
		
		 logger.log(Status.INFO, "Enter the Name");
		 driver.navigate().refresh();
		 enterName(data[1]);
		 logger.log(Status.INFO, "Enter the Organization Name");
		 enterOrganizationName(data[2]);
		 logger.log(Status.INFO, "Enter the Email");
		 EnterEmail(data[5]);
		 logger.log(Status.INFO, "Enter the Contact Number");
		 EnterContactNumber(data[6]);
		 logger.log(Status.INFO, "Choose the Size");
		 EnterSize(data[4]);
		 submitForm("Invalid_Phone_Number");
        // screenshot("Invalid_Phone_Number"); 
		 logger.addScreenCaptureFromPath(System.getProperty("user.dir") + "\\Screenshots\\Invalid_Phone_Number.png");
	     logger.log(Status.PASS, "Test Case Failed");

	}


	   //Function to invoke the TestNG
		public void invokeTestNG() {
			
			//Invoke TestNG to run as java application
			TestNG test=new TestNG();
			List<String> suites=Lists.newArrayList();
			suites.add("testng.xml");
			test.setTestSuites(suites);
			test.run();	
		}

	      
		  // Main method to Create instance of the class and invoking the TestNG function
	     	public static void main(String[] args) throws InterruptedException {
			
		        Testing handle = new Testing();
			    //invoking 
	     		handle.invokeTestNG();			
					
		  }
		
	     	
	    @AfterTest
	    public void closeBrowser() {
	    	
	    	driver.quit();
	    }
     
	    
	    @AfterTest
		 public void endReport(){
		 extent.flush();
		    }
		
}
