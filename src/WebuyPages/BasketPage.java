package WebuyPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class BasketPage {
	WebDriver driver =  null;
	public BasketPage(WebDriver driver){
		this.driver= driver;
	}

	@FindBy(how=How.XPATH,using ="//div[@class='basketPageBox']/form/table/tbody/tr/td[1]/div/select")
	public WebElement selectQty;
	
	public void selectQty(){
		selectQty.click();
	}
}
