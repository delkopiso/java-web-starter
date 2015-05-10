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

import com.kossyuzokwe.dao.UserRepository;
import com.kossyuzokwe.model.User;
import com.kossyuzokwe.test.MockValues;
import com.kossyuzokwe.test.UserFactoryForTest;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@InjectMocks
	private UserService userService;
	
	@Mock
	private UserRepository userRepository;
	
	private UserFactoryForTest userFactoryForTest = new UserFactoryForTest();

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
	public void deleteUser() {
		// Given
		String id = mockValues.nextId();

		// When
		userService.deleteUser(id);

		// Then
		verify(userRepository).delete(id);
	}

}

