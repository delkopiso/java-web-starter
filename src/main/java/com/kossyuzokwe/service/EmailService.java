package com.kossyuzokwe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.kossyuzokwe.event.OnUserEvent;
import com.kossyuzokwe.model.User;

@Service("emailService")
public class EmailService {

	@Autowired
	private MailSender mailSender;

	public void send(SimpleMailMessage email) {
		mailSender.send(email);
	}

	public SimpleMailMessage constructVerifyEmailMessage(
			OnUserEvent event, User user, String token) {
		String recipientAddress = user.getUserEmail();
		String subject = "Registration Confirmation";
		String confirmationUrl = event.getAppUrl() + "/verify/" + token + ".html";
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(recipientAddress);
		email.setSubject(subject);
		String message = "Your registration was successful. Click the following link to verify your account:";
		email.setText(message + " \r\n" + confirmationUrl);
		email.setFrom("admin@java.web.starter.com");
		return email;
	}
}
