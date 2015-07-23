package Scripts;

import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

 public class SearchTerm
  {
	private WebDriver driver;
	private String baseUrl;
	
	@Before
	 public void setUp() throws Exception 
	     {
			    driver = new FirefoxDriver(); // http://www.ufthelp.com/2015/01/Java-Interface-example-in-Selenium.html
			    
			    driver.manage().window().maximize() ; // To maximise the window
			    baseUrl = "https://www.google.co.in"; 
			    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS); 
		   }

	@Test
		public void SearchValue () throws InterruptedException 
	     {
		    driver.get(baseUrl + "/");
			driver.findElement(By.id("lst-ib")).click();
			driver.findElement(By.id("lst-ib")).clear();
			driver.findElement(By.id("lst-ib")).sendKeys("Selenium"); //Enter desired w
			Thread.sleep(2000);
			System.out.println("Entered: Selenium ");
		/*  
		 driver.findElement(By.name("btnG")).click();  // Clicks on Search button
		 driver.findElement(By.linkText("Selenium - Web Browser Automation")).click(); // Clicks on one of the links present in on the web page 
		*/	
			
	//find the element which we want to Select from auto suggestion
			List <WebElement> ele = driver.findElements (By.xpath(".//*[@id='sbse3']/div[2]")); //xpath of the term which is to be searched.
		        Thread.sleep(2000);
		        for(WebElement item:ele)
		           {
		             	String FinalEle = item.getText();
		              	Thread.sleep(2000);       
		              		if(FinalEle.equalsIgnoreCase("selenium webdriver"))
		              			{
		              				item.click();
		              				Thread.sleep(2000); 
		              			}
		           }	
	     }
}
 /* How WebDriver driver = new FirefoxDriver() works internally?
    Firefox driver is included in the selenium-server-stanalone.jar available in the downloads.
    The driver comes in the form of an xpi (firefox extension) which is added to the firefox profile when you start a new instance of FirefoxDriver.
    https://code.google.com/p/selenium/wiki/FirefoxDriver
 */