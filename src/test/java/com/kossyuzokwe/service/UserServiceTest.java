package com.kossyuzokwe.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.kossyuzokwe.dao.PasswordResetTokenRepository;
import com.kossyuzokwe.dao.UserRepository;
import com.kossyuzokwe.dao.VerificationTokenRepository;
import com.kossyuzokwe.model.PasswordResetToken;
import com.kossyuzokwe.model.User;
import com.kossyuzokwe.model.VerificationToken;
import com.kossyuzokwe.test.MockValues;
import com.kossyuzokwe.test.PasswordResetTokenFactoryForTest;
import com.kossyuzokwe.test.UserFactoryForTest;
import com.kossyuzokwe.test.VerificationTokenFactoryForTest;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@InjectMocks
	private UserService userService;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private VerificationTokenRepository verificationTokenRepository;
	
	@Mock
	private PasswordResetTokenRepository resetTokenRepository;
	
	private UserFactoryForTest userFactoryForTest = new UserFactoryForTest();
	
	private VerificationTokenFactoryForTest verificationTokenFactoryForTest = new VerificationTokenFactoryForTest();
	
	private PasswordResetTokenFactoryForTest resetTokenFactoryForTest = new PasswordResetTokenFactoryForTest();

	private MockValues mockValues = new MockValues();

	@Test
	public void findAll() {
		// Given		
		User user1 = userFactoryForTest.newUser();
		User user2 = userFactoryForTest.newUser();
		List<User> users = Arrays.asList(user1, user2);
		when(userRepository.findAll()).thenReturn(users);

		// When
		List<User> usersFounds = new ArrayList<User>(userService.findAll());

		// Then
		assertEquals(user1.getUserId(), usersFounds.get(0).getUserId());
		assertEquals(user2.getUserId(), usersFounds.get(1).getUserId());
	}

	@Test
	public void findUserByUserId() {
		// Given
		User user = userFactoryForTest.newUser();
		String id = user.getUserId();
		when(userRepository.findByUserId(id)).thenReturn(user);

		// When
		User userFound = userService.findUserByUserId(id);

		// Then
		assertEquals(user.getUserId(),userFound.getUserId());
	}

	@Test
	public void findUserByUserName() {
		// Given
		User user = userFactoryForTest.newUserWithUserName();
		String userName = user.getUserName();
		when(userRepository.findByUserName(userName)).thenReturn(user);

		// When
		User userFound = userService.findUserByUserName(userName);

		// Then
		assertEquals(user.getUserName(),userFound.getUserName());
	}

	@Test
	public void findUserByUserEmail() {
		// Given
		User user = userFactoryForTest.newUserWithEmail();
		String userEmail = user.getUserEmail();
		when(userRepository.findByUserEmail(userEmail)).thenReturn(user);

		// When
		User userFound = userService.findUserByUserEmail(userEmail);

		// Then
		assertEquals(user.getUserEmail(),userFound.getUserEmail());
	}

	@Test
	public void findUserByVerificationToken() {
		// Given
		User user = userFactoryForTest.newUser();
		VerificationToken verificationToken = verificationTokenFactoryForTest.newVerificationToken(user);
		String token = verificationToken.getToken();
		when(verificationTokenRepository.findByToken(token)).thenReturn(verificationToken);
		
		// When
		User userFound = userService.findUserByVerificationToken(token);
		
		// Then
		assertEquals(user.getUserId(), userFound.getUserId());
	}

	@Test
	public void findUserByPasswordResetToken() {
		// Given
		User user = userFactoryForTest.newUser();
		PasswordResetToken passwordResetToken = resetTokenFactoryForTest.newPasswordResetToken(user);
		String token = passwordResetToken.getToken();
		when(resetTokenRepository.findByToken(token)).thenReturn(passwordResetToken);
		
		// When
		User userFound = userService.findUserByPasswordResetToken(token);
		
		// Then
		assertEquals(user.getUserId(), userFound.getUserId());
	}

	@Test
	public void save() {
		// Given
		User user = userFactoryForTest.newUser();
		when(userRepository.save(user)).thenReturn(user);
		
		// When
		User userResult = userService.save(user);

		// Then
		assertEquals(userResult.getUserId(), user.getUserId());
	}

	@Test
	public void deleteUser() {
		// Given
		String id = mockValues.nextId();

		// When
		userService.deleteUser(id);

		// Then
		verify(userRepository).delete(id);
	}

}

