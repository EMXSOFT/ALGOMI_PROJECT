package WebuyPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class LandingPage {
	//Webuy.com Landing page Business Logic functions are in this class	
	public String url ="https://uk.webuy.com/search/index.php?stext=*&section=&catid=956";
	WebDriver driver = null;
	public LandingPage(WebDriver driver){
		this.driver = driver;
	}
	
	@FindBy(how=How.CSS, using="div.searchBuyNowButton > span.listBuyButton_mx")
	public WebElement BuyButton;
	
	@FindBy(how=How.ID,using="buyBasketCount")
	public WebElement ItemCounter;
	
	@FindBy(how=How.CSS, using="td.basketTableCellLnk > a")
	public WebElement ViewBasket;
	
	public BasketPage ClickViewBaskt(){
		ViewBasket.click();
		return PageFactory.initElements(driver, BasketPage.class );
	}
	
	

}
