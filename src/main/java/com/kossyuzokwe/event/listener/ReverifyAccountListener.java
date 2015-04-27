package com.kossyuzokwe.event.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;

import com.kossyuzokwe.dao.VerificationTokenRepository;
import com.kossyuzokwe.event.OnReverifyAccountEvent;
import com.kossyuzokwe.model.User;
import com.kossyuzokwe.model.VerificationToken;
import com.kossyuzokwe.service.EmailService;
import com.kossyuzokwe.service.UserService;

public class ReverifyAccountListener implements ApplicationListener<OnReverifyAccountEvent> {

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private VerificationTokenRepository verificationTokenRepository;

	@Override
	public void onApplicationEvent(OnReverifyAccountEvent event) {
		User user = event.getUser();
		VerificationToken existingToken = verificationTokenRepository.findByUser(user);
		String token = existingToken.getToken();
		VerificationToken newToken = userService.generateNewVerificationToken(token);
		SimpleMailMessage email = emailService.constructVerifyEmailMessage(event, user, newToken.getToken());
		emailService.send(email);
	}

}
