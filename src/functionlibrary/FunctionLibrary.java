/* @Description: s consists of most commonly used functions.   
  *@Author :  Jyoti 
 */
package functionlibrary;

import java.io.File;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import globalvariable.GlobalVariable;

public class FunctionLibrary {

	public WebDriver driver;
	GlobalVariable gobalvariable = new GlobalVariable();

	
/* @Description: In this Function we are going to log-in int Gmail account
	 * @Author : Jyoti 
	 * @Param  : WebDriver driver, String UserName, String PassWord
	 * @Return : boolean (True / False ) based script status execution.
 * */	
	public Boolean Gmail_Login (WebDriver driver)
	{ 
		try 
			{
				String Gmail_UserName = ReadFromExcelFileData("logindetails","UserName", 1);
				String Gmail_Password = ReadFromExcelFileData("logindetails","Password", 1); 
		 
				driver.findElement(By.id("gmail-sign-in")).click();
				WebElement GmailUserName =  driver.findElement(By.id("Email"));
		      if(Gmail_UserName != null)
		      	{
		     			  GmailUserName.clear();
		     			  GmailUserName.sendKeys(Gmail_UserName);
				  
					  WebElement GmailNext =  driver.findElement(By.id("next"));
					  GmailNext.click();
					  
					  WebElement GmailPassword = driver.findElement(By.xpath(".//*[@id='Passwd']"));
					  GmailPassword.clear();
					  GmailPassword.sendKeys(Gmail_Password);
					
				      WebElement Gmail_Signin = driver.findElement(By.id("signIn"));
				      Gmail_Signin.click();
				
				  }
				else{
					
					WebElement GmailPassword = driver.findElement(By.name("Passwd"));
					  GmailPassword.clear();
					  GmailPassword.sendKeys(Gmail_Password);
					
				      WebElement Gmail_Signin = driver.findElement(By.id("signIn"));
				      Gmail_Signin.click();
				}
				return true ;
			} 
		catch (Exception e) 
			{
			e.printStackTrace();
			return false;
			}
	}
	
	/* @Description: read test data from Excel sheet  and store that in string variable.
	 * @Author :  Jyoti 
	 * @Param  : String SheetName, String Column, String Testdata
	 * @Return : String.
	 * */
			public String ReadFromExcelFileData(String SheetName, String Column, int RowNo)
				{
					try {
						
						// creates new instance of workbook & then allocate file path to the same, using File Keyword
						  Workbook workbook = Workbook.getWorkbook(new File(gobalvariable.LoginFilePath)); 
						  Sheet sheet = workbook.getSheet("logindetails");
						int numberOfColumns = sheet.getColumns();
						int ColumnIndex = 0;
						for (int columnCount = 0; columnCount <= numberOfColumns; columnCount++) {
							Cell cellid = sheet.getCell(columnCount, 0);
							String ColumnName1 = cellid.getContents();
							ColumnIndex = columnCount;
							if (ColumnName1.equals(Column)) {
								break;
							}
						}
						
						for (int RowCount = 1; RowCount <= 1000; RowCount++) {

							if (RowCount == RowNo) {
								Cell Dataid = sheet.getCell(ColumnIndex, RowCount); // 1);
								String Value = Dataid.getContents();
								workbook.close();
								return Value;
							}
						}
						workbook.close();
						return null;
					} 
					catch (Exception e) {
						e.printStackTrace();
						return null;
					}
				}
			
/* @Description: This Function is for Mail send through Script if @Test get fail (send PrintTrack through mail)  
  * @Author : Jyoti 
  * @Param  : In this function we need to pass only fromMail,ToMail, Exception 
  * @Return : void.
* */
 

public void SendMail(String fromMail, String tomail, Exception e, String MethodName) {
	String host = ReadFromExcelFileData("FailedScript", "hostname", 1);
	// String host = "localhost";
	Properties properties = System.getProperties();  // Get system properties & Setup mail servers
	properties.setProperty("mail.smtp.host", host);  // Get system properties & Setup mail servers
    Session session = Session.getDefaultInstance(properties); 	// Get the default Session object.
	try 
	  {
			MimeMessage message = new MimeMessage(session);   // Create a default MimeMessage object.
			message.setFrom(new InternetAddress(fromMail));   // Set From: header field of the header.
			message.addRecipients(Message.RecipientType.CC, tomail);  // Set To: header field of the header.
		// message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(MethodName + " Function Script failed");   // Set Subject: header field
			message.setText("your test has failed for script" + MethodName + ExceptionUtils.getRootCauseMessage(e)); //Fill d message
			Transport.send(message);
			System.out.println("Sent Mail successfully....");
	   } 
	catch (MessagingException mex) 
	   {
			mex.printStackTrace();
	   }
}


/* @Description: In this function we need to pass only locator of particular element for which we want to wait and  also  Wait for Element function is used for verification of Element load. 
 * @Author :  Jyoti 
 * @Param  : WebDriver driver, By by
 * @Return : boolean (True / False ) based script status execution.
 * */
public Boolean WaitForElement(WebDriver driver, String locator) 
//public Boolean WaitForElement(WebDriver driver, By by)  //by is type
{
	try {
		 Boolean isElementPresent = false;
		 Boolean isElementVisible = false;
		 /* Boolean isElementPresent = false;
		 for (int count = 0; !isElementPresent && count <=150; Count ++)
		 {
		  driver.findElement(by. isDisplayed());
		  isE;ementPresent = True;
		  if (!isElementPresent) 
		  {
			Try
			{
			Thread.sleep(1000);
			}
			Catch (Exception e) 
			{ e.PrintstackTrace();
			}
			Count++
		 }
		 }
		 return True;
		 }
		 Catch (NoSuchFrameException e) {} 
			}
		 
		*/ 
	
		int count = 0;
		do {
			isElementPresent = driver.findElement(By.xpath(locator)).isDisplayed();

			if (isElementPresent == null) {
				Thread.sleep(1000);
				count++;
			} else {
		
				isElementVisible = driver.findElement(By.xpath(locator)).isEnabled();
				if (isElementVisible == null) 
				{
					Thread.sleep(1000);
					count++;
				}

			}

		} 
		while (isElementPresent == null && count <= 120);
		    if (count == 120) {
			return false;
		} else {
			return true;
		}
	} catch (Exception e) 
	{
		// TO DO Auto-generated catch block
		e.printStackTrace();
		return false;
	}

}




}
