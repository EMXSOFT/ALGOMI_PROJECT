package WebuyPages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RepoConstants {
	
	WebDriver driver = null;
	
	//URL NAME
	public static String URL="https://uk.webuy.com/search/index.php?stext=*&section=&catid=956";
	
	
	//List Of Product
	public static String ProductList ="//div[@class='searchRecord']/div[2]/h1/a";
	
	// Product Buttons	
	public static String productButtons ="div.searchBuyNowButton > span.listBuyButton_mx";
	
	//BasketItems
	public static String BasketItems ="//div[@class='basketPageBox']/form/table/tbody/tr/td[2]";
	
	// SelectQty DropDwon
	public static String SelectQty ="//div[@class='basketPageBox']/form/table/tbody/tr/td[1]/div/select";
	
	// List Product Price
	public static String ListPdctPrice ="//div[@class='basketPageBox']/form/table/tbody/tr/td[4]";
	
	
 

}
