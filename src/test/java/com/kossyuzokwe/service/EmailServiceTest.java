package com.kossyuzokwe.service;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.kossyuzokwe.event.OnUserEvent;
import com.kossyuzokwe.model.User;
import com.kossyuzokwe.test.MockValues;
import com.kossyuzokwe.test.OnUserEventFactoryForTest;
import com.kossyuzokwe.test.UserFactoryForTest;
import com.kossyuzokwe.util.Constants;

@RunWith(MockitoJUnitRunner.class)
public class EmailServiceTest {

	@InjectMocks
	private EmailService emailService;

	@Mock
	private MailSender mailSender;
	
	private UserFactoryForTest userFactoryForTest = new UserFactoryForTest();
	
	private OnUserEventFactoryForTest onUserEventFactoryForTest = new OnUserEventFactoryForTest();

	private MockValues mockValues = new MockValues();

	@Test
	public void constructVerifyEmailMessage() {
		// Given
		User user = userFactoryForTest.newRegisteredUser();
		OnUserEvent event = onUserEventFactoryForTest.newOnRegistrationCompleteEvent(user);
		String token = mockValues.nextUUID().toString();

		// When
		SimpleMailMessage message = emailService.constructVerifyEmailMessage(event, user, token);

		// Then
		assertTrue(message.getSubject().equals(Constants.REGISTRATION_EMAIL_SUBJECT));
		assertTrue(message.getFrom().equals(Constants.SENDER_EMAIL));
		assertTrue(message.getTo()[0].equals(user.getUserEmail()));
		assertTrue(message.getText().contains(token));
		assertTrue(message.getText().contains(Constants.REGISTRATION_EMAIL_PREAMBLE));
		assertTrue(message.getText().contains(event.getAppUrl()));
	}

	@Test
	public void constructResetEmailMessage() {
		// Given
		User user = userFactoryForTest.newRegisteredUser();
		OnUserEvent event = onUserEventFactoryForTest.newOnResetPasswordEvent(user);
		String token = mockValues.nextUUID().toString();

		// When
		SimpleMailMessage message = emailService.constructResetEmailMessage(event, user, token);

		// Then
		assertTrue(message.getSubject().equals(Constants.RESET_EMAIL_SUBJECT));
		assertTrue(message.getFrom().equals(Constants.SENDER_EMAIL));
		assertTrue(message.getTo()[0].equals(user.getUserEmail()));
		assertTrue(message.getText().contains(token));
		assertTrue(message.getText().contains(Constants.RESET_EMAIL_PREAMBLE));
		assertTrue(message.getText().contains(event.getAppUrl()));
	}
}
