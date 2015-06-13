package com.kossyuzokwe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.kossyuzokwe.event.OnUserEvent;
import com.kossyuzokwe.model.User;
import com.kossyuzokwe.util.Constants;

@Service
public class EmailService {
	
	Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private MailSender mailSender;

	public void send(SimpleMailMessage email) {
		mailSender.send(email);
	}

	public SimpleMailMessage constructVerifyEmailMessage(
			OnUserEvent event, User user, String token) {
		String confirmationUrl = event.getAppUrl() + "/verify/" + token + ".html";
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getUserEmail());
		email.setSubject(Constants.REGISTRATION_EMAIL_SUBJECT);
		email.setText(Constants.REGISTRATION_EMAIL_PREAMBLE + " \r\n" + confirmationUrl);
		email.setFrom(Constants.SENDER_EMAIL);
		return email;
	}

	public SimpleMailMessage constructResetEmailMessage(
			OnUserEvent event, User user, String token) {
		String confirmationUrl = event.getAppUrl() + "/reset/" + user.getUserId() + "/" + token + ".html";
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getUserEmail());
		email.setSubject(Constants.RESET_EMAIL_SUBJECT);
		email.setText(Constants.RESET_EMAIL_PREAMBLE + " \r\n" + confirmationUrl);
		email.setFrom(Constants.SENDER_EMAIL);
		return email;
	}
}
