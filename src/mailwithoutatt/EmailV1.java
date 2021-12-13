package mailwithoutatt;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailV1 implements SendMailToAbsentees,Serializable {
	
	public EmailV1() throws RemoteException {
		
	}
	
	public Session Authentication1() {
		final String username = "frommailid";
		final String password = "password";
		
		String host = "smtp.gmail.com";
	    Properties props = System.getProperties();
	    props.put("mail.smtp.host", host);
	    props.put("mail.smtp.user", username);
	    props.put("mail.smtp.password", password);
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.port", "587"); 
	    props.put("mail.smtp.auth", "true");
		
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username,password);
			}
		});
		
		return session;
		
	}
	
	public void Notification1(ArrayList<String> toEmail) {
		
		String fromEmail = "frommailid";
		
		Session session = Authentication1();
		
		//Start our mail message
		MimeMessage msg = new MimeMessage(session);
		try {
			Scanner sc = new Scanner(System.in);
			
			msg.setFrom(new InternetAddress(fromEmail));
			
			for(int i=0;i<toEmail.size();i++) {
			    msg.addRecipient(Message.RecipientType.CC, new InternetAddress(toEmail.get(i)));
			}
			
			msg.setSubject("Today's absentees");
			
			Multipart emailContent = new MimeMultipart();
			
			//Text body part
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText("Today, your ward was absent to school.");
			
			//Attach body parts
			emailContent.addBodyPart(textBodyPart);
			
			//Attach multipart to message
			msg.setContent(emailContent);
			
			Transport.send(msg);
			System.out.println("Sent message");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
