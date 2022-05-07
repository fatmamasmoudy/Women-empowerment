package tn.esprit.spring.services;

import javax.mail.MessagingException;

import tn.esprit.spring.entities.Mail;

public interface IMailService {
	public void sendEmail(Mail mail);
	
	void envoyer(String sub, String content, String attachment, String sendTo) throws MessagingException ;
	
	void sendMailWithAttachment(String attachment, String sendTo) throws MessagingException ;
}
