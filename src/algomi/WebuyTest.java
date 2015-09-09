package algomi;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import WebuyPages.BasketPage;
import WebuyPages.LandingPage;
import WebuyPages.RepoConstants;

public class WebuyTest 
{
	
	//https://uk.webuy.com/search/index.php?stext=*&section=&catid=956
	//Re-write XLS reader Class for  this project
	//Add the POI api files to the project build part
	//Read the data from the excel file
	//Create an object of the XLS reader class
	//Do a dry RUN to check to make sure this XLS file is detected.
	WebDriver driver;
	LandingPage lp = PageFactory.initElements(driver, LandingPage.class);
    WebElement Vb = lp.ViewBasket;	
    BasketPage Bp = PageFactory.initElements(driver, BasketPage.class);
    @SuppressWarnings("deprecation")
	@Test
	public void testProductShoppingBasket() throws InterruptedException
	{
			
	 Xls_Reader XLS = new Xls_Reader(System.getProperty("user.dir") + "\\ALGOMI\\WeBuy.xlsx" );
 
	 int Rows = XLS.getRowCount("Products");
	 System.out.println("ROWCOUNT = "+ Rows);
	 //Create Two ArrayList to Add Prdt and Qty  from the Excel File
	 List<String> ProductNamesToBeAdded = new ArrayList<String>();
	 Hashtable<String,String> ProductQuantityToBeAdded = new Hashtable<String,String>();
	
	 //Print the Products and Product Quantities in the Excel File 
	for(int i=2; i<=Rows; i++)
	{
		String Pdts  = 	 XLS.getCellData("Products","Product",i);
		String Qty =	 XLS.getCellData("Products","Quantity",i);
		System.out.println("Printing Products & Quantity from Excel Sheet: "+ Pdts + "  ----  " + "Printing QTY: "+ Qty);
	    //Add the Pdts and Qtys from Excel Sheet to ArrayList
		ProductNamesToBeAdded.add(Pdts);
		ProductQuantityToBeAdded.put(Pdts,Qty);
	}
	//Set Chrome Driver Part
	//System.setProperty("webdriver.chrome.driver","C:\\GRID\\chromedriver.exe");
	//WebDriver driver = new ChromeDriver();
	ProfilesIni Prof = new ProfilesIni(); 
	FirefoxProfile p = Prof.getProfile("STEELO"); 
	driver = new FirefoxDriver(p);  
    
	//WebDriver driver = new FirefoxDriver();
	//driver.get(RepoConstants.URL);
	driver.get(lp.url);
	driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
	driver.manage().window().maximize();
	
	
	// Find Pdts which you need to Add to basket, from the Excel sheet
	List<WebElement> PdctList = driver.findElements(By.xpath(RepoConstants.ProductList));
	List<WebElement> PdtButtons = driver.findElements(By.cssSelector(RepoConstants.productButtons));
	System.out.println("Printing PdctList ---- " + PdctList.size());
	System.out.println("Printing PdctButtons ---- " + PdtButtons.size());
	Thread.sleep(5000);
	    
	int PrdtCount = 0;
	for(int i = 0; i<PdctList.size(); i++)
	{		
		 
		 System.out.println("Printing List of Games ---- " + PdctList.get(i).getText());
	     if(ProductNamesToBeAdded.contains(PdctList.get(i).getText()))
	     {
	       System.out.println("Found Pdt from ExcelSheet ---- " + PdctList.get(i).getText());
	       PdtButtons.get(i).click();
	
	       // Validate the basket 
	       String count = driver.findElement(By.id("buyBasketCount")).getText();
	       System.out.println("BASKET-COUNT " + count);
	       Assert.assertEquals(count, String.valueOf(PrdtCount+1));
	       PdctList = driver.findElements(By.xpath(RepoConstants.ProductList));
	   	   PdtButtons = driver.findElements(By.cssSelector(RepoConstants.productButtons));
	       PrdtCount++;
	    } 
	
	System.out.println("******** ----**************----************ ");
	}	
	// change the quantities	 
	driver.findElement(By.cssSelector("td.basketTableCellLnk > a")).click();
	//List<WebElement> secondCol = driver.findElements(By.cssSelector("div.basketPageBox > form > table > tr > td:nth-child(3)"));
	// Reload Element to avoid Stale Element Exception
	List<WebElement> BasketItems = driver.findElements(By.xpath(RepoConstants.BasketItems));
	List<WebElement> selectQty = driver.findElements(By.xpath(RepoConstants.SelectQty));
	     
	
	    for(int i =0; i <BasketItems.size()-2; i++)
	     {
	    	 System.out.println("Priting Basket Items Info with split   " +BasketItems.get(i).getText().split("\\n")[0] );
	    	 //Split the Product text by space and extract the 0th array
	    	 String key = BasketItems.get(i).getText().split("\\n")[0];
	    	 //BasketItems = driver.findElements(By.xpath(RepoConstants.BasketItems));
	    	 System.out.println("Printing Key from Split  " + "NO "+ (i+1) +"==== " + key + "------====-------  "+ ProductQuantityToBeAdded.get(key));
	    	 System.out.println("***************--------------------**********************");
		     WebElement dropdown = selectQty.get(i);
	    	 Select S = new Select(dropdown);
	    	 S.selectByVisibleText(ProductQuantityToBeAdded.get(key));
	    	 // Reload Element to avoid Stale Element Exception
	    	 BasketItems = driver.findElements(By.xpath(RepoConstants.BasketItems));
	    	 selectQty = driver.findElements(By.xpath(RepoConstants.SelectQty));
		     
	     }
	
	    List<WebElement> ListPdctPrice = driver.findElements(By.xpath(RepoConstants.ListPdctPrice));
		double sum =0.0;
	    for (int i=0; i<ListPdctPrice.size(); i ++)
		 {
	    	// SPlit Pdct Price to Remove the Pound
			String value = ListPdctPrice.get(i).getText().split("\\£")[1];
			System.out.println("Printing Amount "+value);
			sum = sum + Double.parseDouble(value);
			System.out.println("Printing Total Price  " +sum);
			
		 }
	        System.out.println("FINAL PRINTING SUM  " +sum);
	        //Total Number of Rows 
	        int totRows = driver.findElements(By.xpath("//div[@class='basketPageBox']/form/table/tbody/tr")).size();
	        System.out.println("PRINTING ROWSTOT " + totRows);
	        String Delcharges = driver.findElement(By.xpath("//div[@class='basketPageBox']/form/table/tbody/tr["+(totRows-1)+"]/td[2]")).getText();
	        System.out.println("DELIVERY CHARGES  " +Delcharges);
	        String FinalChargs = driver.findElement(By.xpath("//div[@class='basketPageBox']/form/table/tbody/tr["+(totRows)+"]/td[2]")).getText();
	        System.out.println("TOTAL FINAL CHARGES "+FinalChargs);
	        Double ActualCharges = sum + Double.parseDouble(Delcharges.split("\\£")[1]);
	        Double ExpectedCharges = Double.parseDouble(FinalChargs.split("\\£")[1]);
	        System.out.println("PRINTING ACTUAL CHARGES " + ActualCharges);
	        System.out.println("PRINTING EXPECTED CHARGES " + ExpectedCharges);
	        Assert.assertEquals(ExpectedCharges, ActualCharges);
	        
	       
	    }
	@AfterTest
	public void printResult() throws InterruptedException{
		driver.get("file:///C:/Users/EA_SLIM/Desktop/ALGOMI_DEMO/AlgomiDemo/XSLT_Reports/output/index.html");
		Thread.sleep(50000);
		
		
	}
	
	@AfterTest
     public void tearDown(){
    	 driver.quit();
     }
}



