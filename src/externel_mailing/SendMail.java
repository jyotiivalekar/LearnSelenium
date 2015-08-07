/* @Description: Following code helps user to Send an email through Gmail account  
	 * @Author :  Gaurav Singh 
 */
package externel_mailing;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
	public static void main(String args[]){
	final String username = "<E-mail>@gmail.com";
	final String password = "<Password>";
	Properties props = new Properties();
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.port", "587");
	
	Session session = Session.getInstance(props,new javax.mail.Authenticator() {
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
	});
	
	try {
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress("<From E-mail>@gmail.com"));
		message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("To E-Mail ID"));
		message.setSubject("Testing Subject");
		message.setText("Dear,"+ "\n\n Yupiee This mail is sent through Selenium Webdriver!");
		Transport.send(message);
		System.out.println("Mail is sucesfully sent");
	} catch (MessagingException e) {
			throw new RuntimeException(e);
	}
	}
}
