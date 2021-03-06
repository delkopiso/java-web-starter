package com.kossyuzokwe.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kossyuzokwe.dao.PasswordResetTokenRepository;
import com.kossyuzokwe.dao.RoleRepository;
import com.kossyuzokwe.dao.UserRepository;
import com.kossyuzokwe.dao.VerificationTokenRepository;
import com.kossyuzokwe.model.PasswordResetToken;
import com.kossyuzokwe.model.Role;
import com.kossyuzokwe.model.User;
import com.kossyuzokwe.model.VerificationToken;
import com.kossyuzokwe.test.MockValues;
import com.kossyuzokwe.test.PasswordResetTokenFactoryForTest;
import com.kossyuzokwe.test.RoleFactoryForTest;
import com.kossyuzokwe.test.UserFactoryForTest;
import com.kossyuzokwe.test.VerificationTokenFactoryForTest;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@InjectMocks
	private UserService userService;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private RoleRepository roleRepository;
	
	@Mock
	private VerificationTokenRepository verificationTokenRepository;
	
	@Mock
	private PasswordResetTokenRepository resetTokenRepository;
	
	@Mock
	private PasswordEncoder passwordEncoder;
	
	private UserFactoryForTest userFactoryForTest = new UserFactoryForTest();
	
	private RoleFactoryForTest roleFactoryForTest = new RoleFactoryForTest();
	
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
		VerificationToken verificationToken = verificationTokenFactoryForTest.newVerificationToken(user, false);
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
		PasswordResetToken passwordResetToken = resetTokenFactoryForTest.newPasswordResetToken(user, false);
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

	@Test
	public void registerNewUserAccount() {
		// Given
		User user = userFactoryForTest.newRegisteredUser();
		Role role = roleFactoryForTest.newRoleWithName();
		when(userRepository.save(user)).thenReturn(user);
		when(roleRepository.findByRoleName(role.getRoleName())).thenReturn(role);

		// When
		User userResult = userService.registerNewUserAccount(user);

		// Then
		assertEquals(userResult.getUserId(), user.getUserId());
		assertEquals(userResult.getUserName(), user.getUserName());
		assertEquals(userResult.getUserEmail(), user.getUserEmail());
		assertEquals(userResult.getUserPassword(), userResult.getUserPassword());
	}

	@Test
	public void createVerificationTokenForUser() {
		// Given
		User user = userFactoryForTest.newUser();
		VerificationToken verificationToken = verificationTokenFactoryForTest.newVerificationToken(user, false);
		when(verificationTokenRepository.save(verificationToken)).thenReturn(verificationToken);
		
		//When
		VerificationToken result = verificationTokenRepository.save(verificationToken);
		
		// Then
		assertEquals(verificationToken.getVerificationId(), result.getVerificationId());
	}

	@Test
	public void getVerificationToken() {
		// Given
		User user = userFactoryForTest.newUser();
		VerificationToken verificationToken = verificationTokenFactoryForTest.newVerificationToken(user, false);
		String token = verificationToken.getToken();
		when(verificationTokenRepository.findByToken(token)).thenReturn(verificationToken);

		//When
		VerificationToken result = userService.getVerificationToken(token);
		
		// Then
		assertEquals(verificationToken.getVerificationId(), result.getVerificationId());
	}

	@Test
	public void regenerateVerificationToken() {
		// Given
		User user = userFactoryForTest.newUser();
		VerificationToken verificationToken = verificationTokenFactoryForTest.newVerificationToken(user, false);
		String existingToken = verificationToken.getToken();
		when(verificationTokenRepository.findByToken(existingToken)).thenReturn(verificationToken);
		when(verificationTokenRepository.save(verificationToken)).thenReturn(verificationToken);

		//When
		VerificationToken result = userService.regenerateVerificationToken(existingToken);
		
		// Then
		assertEquals(verificationToken.getVerificationId(), result.getVerificationId());
		assertNotEquals(existingToken, result.getToken());
	}

	@Test
	public void createPasswordResetTokenForUser() {
		// Given
		User user = userFactoryForTest.newUser();
		PasswordResetToken resetToken = resetTokenFactoryForTest.newPasswordResetToken(user, false);
		when(resetTokenRepository.save(resetToken)).thenReturn(resetToken);
		
		//When
		PasswordResetToken result = resetTokenRepository.save(resetToken);
		
		// Then
		assertEquals(resetToken.getPasswordResetTokenId(), result.getPasswordResetTokenId());
	}

	@Test
	public void getPasswordResetToken() {
		// Given
		User user = userFactoryForTest.newUser();
		PasswordResetToken resetToken = resetTokenFactoryForTest.newPasswordResetToken(user, false);
		String token = resetToken.getToken();
		when(resetTokenRepository.findByToken(token)).thenReturn(resetToken);

		//When
		PasswordResetToken result = userService.getPasswordResetToken(token);
		
		// Then
		assertEquals(resetToken.getPasswordResetTokenId(), result.getPasswordResetTokenId());
	}

	@Test
	public void changeUserPassword() {
		// Given
		User user = userFactoryForTest.newUserWithPassword();
		String password = mockValues.nextString(mockValues.nextInteger());
		when(passwordEncoder.encode(password)).thenReturn(password);
		when(userRepository.save(user)).thenReturn(user);

		// When
		User userResult = userService.changeUserPassword(user, password);

		// Then
		assertEquals(password, userResult.getUserPassword());
	}

	@Test
	public void validOldPasswordFalse() {
		// Given
		User user = userFactoryForTest.newUserWithPassword();
		String password = mockValues.nextString(mockValues.nextInteger());

		// When
		boolean matches = userService.validOldPassword(user, password);

		// Then
		assertFalse(matches);
	}

	@Test
	public void emailExistsTrue() {
		// Given
		User user = userFactoryForTest.newUserWithEmail();
		when(userRepository.findByUserEmail(user.getUserEmail())).thenReturn(user);
		
		//When
		boolean exists = userService.emailExists(user.getUserEmail());
		
		// Then
		assertTrue(exists);
	}

	@Test
	public void emailExistsFalse() {
		// Given
		User user = userFactoryForTest.newUserWithEmail();
		when(userRepository.findByUserEmail(user.getUserEmail())).thenReturn(null);
		
		//When
		boolean exists = userService.emailExists(user.getUserEmail());
		
		// Then
		assertFalse(exists);
	}

}
