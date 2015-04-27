package com.kossyuzokwe.event.listener;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;

import com.kossyuzokwe.event.OnResetPasswordEvent;
import com.kossyuzokwe.model.User;
import com.kossyuzokwe.service.EmailService;
import com.kossyuzokwe.service.UserService;

public class ResetPasswordListener implements ApplicationListener<OnResetPasswordEvent> {
	
	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	@Override
	public void onApplicationEvent(OnResetPasswordEvent event) {
		User user = event.getUser();
		String token = UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(user, token);
		SimpleMailMessage email = emailService.constructResetEmailMessage(event, user, token);
		emailService.send(email);
	}

}
