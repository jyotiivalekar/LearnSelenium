package Scripts;

import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import functionlibrary.FunctionLibrary;
import globalvariable.GlobalVariable;

public class SignInGmail {
	
		//private WebDriver driver;
		private String baseURL;
		FunctionLibrary functionlibrary = new FunctionLibrary();
		GlobalVariable globalvariable = new GlobalVariable();
		ProfilesIni profile = new ProfilesIni();
		FirefoxProfile ffprofile = profile.getProfile("default");
		WebDriver driver = new FirefoxDriver(ffprofile);
	
	@Before
	public void setUp() 
	{
		
	//	driver = new FirefoxDriver(ffprofile);
		driver.manage().window().maximize() ;
		baseURL = globalvariable.gmailUrl; 
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}


	@Test
	public void GmailLogin() throws InterruptedException {
		try 
		{
			driver.get(baseURL + "/");
			String Gmail_UserName =  functionlibrary.ReadFromExcelFileData("logindetails","UserName", 1);
			String Gmail_Password = functionlibrary.ReadFromExcelFileData("logindetails","Password", 1); 
		    System.out.println("Gmail UserName : " + Gmail_UserName);
		    System.out.println("Gmail Password : "+ Gmail_Password);
		    Boolean isLogin = functionlibrary.Gmail_Login(driver); //Calls login function written to log in into gmail account.
		    driver.manage().timeouts().pageLoadTimeout(30,TimeUnit.SECONDS);
		    Assert.assertTrue("Failed to login", isLogin);
		    Thread.sleep(4000);
		    driver.findElement(By.xpath(".//*[@id='gb']/div[1]/div[1]/div/div[4]/div[1]/a")).click(); //Clicks on setting to logout
		    driver.findElement(By.id("gb_71")).click(); //Clicks on Logout button
	   }
	 
	catch (Exception e)
	{
		e.printStackTrace();
		String FromName = functionlibrary.ReadFromExcelFileData("FailedScript", "FromName", 1);
		String ToName = functionlibrary.ReadFromExcelFileData("FailedScript", "ToList", 1);
		functionlibrary.SendMail(FromName, ToName, e, globalvariable.MethodNames);
		System.out.println("Mail send for" + globalvariable.MethodNames);
		//Host Name is imap.gmail.com. User Name is your full Gmail address, including '@gmail.com' 
		//For Outgoing Mail Server (SMTP), Host Name is smtp.gmail.com.
	}
}
		
		
	@After
 	  public void tearDown() throws Exception 
	{
       driver.close();
 		try {
 		      Thread.sleep(5000);
 		      driver.quit();
 		      }
 		catch (Exception e) { } 
	
	}
}