package tn.esprit.spring.controllers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;



public class SendMail {
	
	public static void email(String reponse,String mailrep, String name ) {
		Properties properties = new Properties();

        //Enable authentication
        properties.put("mail.smtp.auth", "true");
        //Set TLS encryption enabled
        properties.put("mail.smtp.starttls.enable", "true");
        //Set SMTP host
        properties.put("mail.smtp.host", "smtp.gmail.com");
        //Set smtp port
        properties.put("mail.smtp.port", "587");

        //Your gmail address
        String myAccountEmail = "******email***";
        //Your gmail password
        String password = "*****pass**";

        //Create a session with account credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("****email***", "***pass****");
            }
        });

        //Prepare email message
        Message message = new MimeMessage(session);
        try {
			message.setFrom(new InternetAddress(myAccountEmail));
			  message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailrep));
		        message.setSubject("Reclamation Response");
		        String htmlCode = "<p> Mr/Mrs "+name+" </p> <p>"+reponse+"</p>";
		        message.setContent(htmlCode, "text/html");

		        //Send mail
		        Transport.send(message);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
        System.out.println("Message sent successfully");	
	
		
	}
	 
	    
	
}
