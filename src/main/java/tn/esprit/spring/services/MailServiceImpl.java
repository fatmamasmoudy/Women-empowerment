package tn.esprit.spring.services;

import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.core.io.FileSystemResource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Mail;

@Service
public class MailServiceImpl implements IMailService {

	@Autowired
    JavaMailSender mailSender;
 
	@Override
    public void sendEmail(Mail mail) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
 
        try {
 
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
 
            mimeMessageHelper.setSubject(mail.getMailSubject());
            mimeMessageHelper.setFrom(new InternetAddress(mail.getMailFrom(), "ahmedhajsaid@gmail.com"));
            mimeMessageHelper.setTo(mail.getMailTo());
            mimeMessageHelper.setText(mail.getContentType());
 
            mailSender.send(mimeMessageHelper.getMimeMessage());
 
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        
        
    }
	@Override
	public void envoyer(String sub, String content, String attachment, String sendTo)throws MessagingException{
		MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setFrom("ahmedhajsaid@gmail.com");
        mimeMessageHelper.setTo(sendTo);
        mimeMessageHelper.setText(content);
        mimeMessageHelper.setSubject(sub);

        FileSystemResource fileSystemResource=
                new FileSystemResource(new File(attachment));
        mimeMessageHelper.addAttachment(fileSystemResource.getFilename(),fileSystemResource);
        javaMailSender.send(mimeMessage);
        System.out.println("Mail with attachment sent successfully..");


    }

	
	@Autowired
    private JavaMailSender javaMailSender;

    public void sendMailWithAttachment(String attachment, String sendTo) throws MessagingException {
//    	  String toEmail,
//        String body,
//        String subject,
//        String attachment

        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true);
        mimeMessageHelper.setFrom("ahmedhajsaid@gmail.com");
        mimeMessageHelper.setTo(sendTo);
        mimeMessageHelper.setText("");
        mimeMessageHelper.setSubject("Résultat quiz");

        FileSystemResource fileSystemResource=
                new FileSystemResource(new File(attachment));
        mimeMessageHelper.addAttachment(fileSystemResource.getFilename(),fileSystemResource);
        javaMailSender.send(mimeMessage);
        System.out.println("Mail with attachment sent successfully..");


    }


}
