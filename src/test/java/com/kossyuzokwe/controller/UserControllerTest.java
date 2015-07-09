package com.kossyuzokwe.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.kossyuzokwe.event.OnRegistrationCompleteEvent;
import com.kossyuzokwe.event.OnResetPasswordEvent;
import com.kossyuzokwe.event.OnReverifyAccountEvent;
import com.kossyuzokwe.model.PasswordChange;
import com.kossyuzokwe.model.PasswordResetToken;
import com.kossyuzokwe.model.User;
import com.kossyuzokwe.model.VerificationToken;
import com.kossyuzokwe.service.UserService;
import com.kossyuzokwe.test.MockValues;
import com.kossyuzokwe.test.OnUserEventFactoryForTest;
import com.kossyuzokwe.test.PasswordResetTokenFactoryForTest;
import com.kossyuzokwe.test.UserFactoryForTest;
import com.kossyuzokwe.test.VerificationTokenFactoryForTest;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

	@InjectMocks
	private UserController userController;
	
	@Mock
	private UserService userService;

    @Mock
    private ApplicationEventPublisher eventPublisher;
	
	@Mock
	private HttpServletRequest request;
	
	private UserFactoryForTest userFactoryForTest = new UserFactoryForTest();
	
	private OnUserEventFactoryForTest userEventFactoryForTest = new OnUserEventFactoryForTest();
	
	private VerificationTokenFactoryForTest verificationTokenFactoryForTest = new VerificationTokenFactoryForTest();
	
	private PasswordResetTokenFactoryForTest resetTokenFactoryForTest = new PasswordResetTokenFactoryForTest();
	
	private MockValues mockValues = new MockValues();

	@Test
	public void showRegister() {
		// When
		String viewName = userController.showRegister();

		// Then
		assertEquals("auth/signup", viewName);
	}

	@Test
	public void doRegisterOk() {
		// Given
		User user = userFactoryForTest.newRegisteredUser();
		BindingResult bindingResult = new BeanPropertyBindingResult(user, "user");
		RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
		OnRegistrationCompleteEvent event = userEventFactoryForTest.newOnRegistrationCompleteEvent(user);
		when(userService.registerNewUserAccount(user)).thenReturn(user);
		doNothing().when(eventPublisher).publishEvent(any(event.getClass()));
		
		// When
		String viewName = userController.doRegister(user, request, bindingResult, redirectAttributes);
		
		// Then
		verify(eventPublisher).publishEvent(any(event.getClass()));
		Map<String, ?> redirectMap = redirectAttributes.getFlashAttributes();
		assertTrue(Boolean.parseBoolean(redirectMap.get("success").toString()));
		assertEquals("redirect:/signup.html", viewName);
	}
	
	@Test
	public void doRegisterError() {
		// Given
		User user = new User();
		BindingResult bindingResult = new BeanPropertyBindingResult(user, "user");
		bindingResult.addError(new ObjectError("user","Non-unique field"));
		RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
		
		// When
		String viewName = userController.doRegister(user, request, bindingResult, redirectAttributes);
		
		// Then
		assertEquals("auth/signup", viewName);
	}

	@Test
	public void availableNameTrue() {
		// Given
		String username = mockValues.nextString(mockValues.nextInteger());
		
		// When
		String result = userController.availableName(username);
		
		// Then
		assertTrue(Boolean.parseBoolean(result));
	}

	@Test
	public void availableNameFalse() {
		// Given
		User user = userFactoryForTest.newUserWithUserName();
		String username = user.getUserName();
		when(userService.findUserByUserName(username)).thenReturn(user);
		
		// When
		String result = userController.availableName(username);
		
		// Then
		assertFalse(Boolean.parseBoolean(result));
	}

	@Test
	public void emailExistsTrue() {
		// Given
		User user = userFactoryForTest.newUserWithEmail();
		String email = user.getUserEmail();
		when(userService.emailExists(email)).thenReturn(true);
		
		// When
		String result = userController.emailExists(email);
		
		// Then
		assertFalse(Boolean.parseBoolean(result));
	}

	@Test
	public void emailExistsFalse() {
		// Given
		String email = mockValues.nextString(mockValues.nextInteger());
		when(userService.emailExists(email)).thenReturn(false);
		
		// When
		String result = userController.emailExists(email);
		
		// Then
		assertTrue(Boolean.parseBoolean(result));
	}

	@Test
	public void verifyInvalid() {
		// Given
		Model model = new ExtendedModelMap();
		User user = userFactoryForTest.newRegisteredUser();
		VerificationToken verificationToken = verificationTokenFactoryForTest.newVerificationToken(user, false);
		String token = verificationToken.getToken();
		RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
		when(userService.getVerificationToken(token)).thenReturn(null);
		
		// When
		String viewName = userController.verify(model, token, redirectAttributes);
		
		// Then
		Map<String, ?> modelMap = model.asMap();
		assertTrue(Boolean.parseBoolean(modelMap.get("invalid").toString()));
		Map<String, ?> redirectMap = redirectAttributes.getFlashAttributes();
		assertTrue(redirectMap.isEmpty());
		assertEquals("auth/failed", viewName);
	}

	@Test
	public void verifyExpired() {
		// Given
		Model model = new ExtendedModelMap();
		User user = userFactoryForTest.newRegisteredUser();
		VerificationToken verificationToken = verificationTokenFactoryForTest.newVerificationToken(user, true);
		String token = verificationToken.getToken();
		RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
		when(userService.getVerificationToken(token)).thenReturn(verificationToken);
		
		// When
		String viewName = userController.verify(model, token, redirectAttributes);
		
		// Then
		Map<String, ?> modelMap = model.asMap();
		assertTrue(Boolean.parseBoolean(modelMap.get("expired").toString()));
		assertEquals(token, modelMap.get("token"));
		Map<String, ?> redirectMap = redirectAttributes.getFlashAttributes();
		assertTrue(redirectMap.isEmpty());
		assertEquals("auth/failed", viewName);
	}

	@Test
	public void verifyOk() {
		// Given
		Model model = new ExtendedModelMap();
		User user = userFactoryForTest.newRegisteredUser();
		VerificationToken verificationToken = verificationTokenFactoryForTest.newVerificationToken(user, false);
		String token = verificationToken.getToken();
		RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
		when(userService.getVerificationToken(token)).thenReturn(verificationToken);
		when(userService.save(user)).thenReturn(user);
		
		// When
		String viewName = userController.verify(model, token, redirectAttributes);
		
		// Then
		assertTrue(user.isUserEnabled());
		Map<String, ?> modelMap = model.asMap();
		assertTrue(modelMap.isEmpty());
		Map<String, ?> redirectMap = redirectAttributes.getFlashAttributes();
		assertTrue(Boolean.parseBoolean(redirectMap.get("verified").toString()));
		assertEquals("redirect:/account.html", viewName);
	}
	
	@Test
	public void reverify() {
		// Given
		Model model = new ExtendedModelMap();
		User user = userFactoryForTest.newRegisteredUser();
		String existingToken = mockValues.nextString(mockValues.nextInteger());
		VerificationToken newToken = verificationTokenFactoryForTest.newVerificationToken(user, false);
		OnReverifyAccountEvent event = userEventFactoryForTest.newOnReverifyAccountEvent(user);
		when(userService.regenerateVerificationToken(existingToken)).thenReturn(newToken);
		when(userService.findUserByVerificationToken(newToken.getToken())).thenReturn(user);
		doNothing().when(eventPublisher).publishEvent(any(event.getClass()));
		
		// When
		String viewName = userController.reverify(model, request, existingToken);
		
		// Then
		verify(eventPublisher).publishEvent(any(event.getClass()));
		Map<String, ?> modelMap = model.asMap();
		assertTrue(Boolean.parseBoolean(modelMap.get("resent").toString()));
		assertEquals("auth/failed", viewName);
	}

	@Test
	public void login() {
		// When
		String viewName = userController.login();

		// Then
		assertEquals("auth/signin", viewName);
	}

	@Test
	public void showForgot() {
		// When
		String viewName = userController.showForgot();

		// Then
		assertEquals("password/forgot", viewName);
	}
	
	@Test
	public void doForgotUserExists(){
		// Given
		User user = userFactoryForTest.newRegisteredUser();
		RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
		OnResetPasswordEvent event = userEventFactoryForTest.newOnResetPasswordEvent(user);
		when(userService.findUserByUserEmail(user.getUserEmail())).thenReturn(user);
		doNothing().when(eventPublisher).publishEvent(any(event.getClass()));
		
		// When
		String viewName = userController.doForgot(user, request, redirectAttributes);

		// Then
		verify(eventPublisher).publishEvent(any(event.getClass()));
		Map<String, ?> redirectMap = redirectAttributes.getFlashAttributes();
		assertTrue(Boolean.parseBoolean(redirectMap.get("exists").toString()));
		assertEquals("redirect:/forgot.html", viewName);
	}
	
	@Test
	public void doForgotUserDoesNotExist() {
		// Given
		User user = userFactoryForTest.newRegisteredUser();
		RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
		OnResetPasswordEvent event = userEventFactoryForTest.newOnResetPasswordEvent(user);
		when(userService.findUserByUserEmail(user.getUserEmail())).thenReturn(null);
		doNothing().when(eventPublisher).publishEvent(any(event.getClass()));
		
		// When
		String viewName = userController.doForgot(user, request, redirectAttributes);

		// Then
		verify(eventPublisher, never()).publishEvent(any(event.getClass()));
		Map<String, ?> redirectMap = redirectAttributes.getFlashAttributes();
		assertFalse(Boolean.parseBoolean(redirectMap.get("exists").toString()));
		assertEquals("redirect:/forgot.html", viewName);
	}

	@Test
	public void showResetExpired() {
		// Given
		Model model = new ExtendedModelMap();
		User user = userFactoryForTest.newRegisteredUser();
		String id = user.getUserId();
		PasswordResetToken resetToken = resetTokenFactoryForTest.newPasswordResetToken(user, true);
		String token = resetToken.getToken();
		when(userService.getPasswordResetToken(token)).thenReturn(resetToken);
		when(userService.findUserByUserId(id)).thenReturn(user);
		
		// When
		String viewName = userController.showReset(model, id, token);

		// Then
		Map<String, ?> modelMap = model.asMap();
		assertTrue(Boolean.parseBoolean(modelMap.get("expired").toString()));
		assertEquals("password/reset", viewName);
	}
	
	@Test
	public void showResetInvalidNotNull() {
		// Given
		Model model = new ExtendedModelMap();
		User user = userFactoryForTest.newRegisteredUser();
		String id = user.getUserId();
		PasswordResetToken resetToken = resetTokenFactoryForTest.newPasswordResetToken(user, false);
		String token = resetToken.getToken();
		when(userService.getPasswordResetToken(token)).thenReturn(resetToken);
		when(userService.findUserByUserId(id)).thenReturn(null);
		
		// When
		String viewName = userController.showReset(model, id, token);

		// Then
		Map<String, ?> modelMap = model.asMap();
		assertTrue(Boolean.parseBoolean(modelMap.get("invalid").toString()));
		assertEquals("password/reset", viewName);
	}
	
	@Test
	public void showResetInvalidNull() {
		// Given
		Model model = new ExtendedModelMap();
		User user = userFactoryForTest.newRegisteredUser();
		String id = user.getUserId();
		PasswordResetToken resetToken = resetTokenFactoryForTest.newPasswordResetToken(user, false);
		String token = resetToken.getToken();
		when(userService.getPasswordResetToken(token)).thenReturn(null);
		when(userService.findUserByUserId(id)).thenReturn(user);
		
		// When
		String viewName = userController.showReset(model, id, token);

		// Then
		Map<String, ?> modelMap = model.asMap();
		assertTrue(Boolean.parseBoolean(modelMap.get("invalid").toString()));
		assertEquals("password/reset", viewName);
	}
	
	@Test
	public void showResetOk() {
		// Given
		Model model = new ExtendedModelMap();
		User user = userFactoryForTest.newRegisteredUser();
		String id = user.getUserId();
		PasswordResetToken resetToken = resetTokenFactoryForTest.newPasswordResetToken(user, false);
		String token = resetToken.getToken();
		when(userService.getPasswordResetToken(token)).thenReturn(resetToken);
		when(userService.findUserByUserId(id)).thenReturn(user);
		
		// When
		String viewName = userController.showReset(model, id, token);

		// Then
		Map<String, ?> modelMap = model.asMap();
		assertEquals(id, modelMap.get("id"));
		assertEquals("password/reset", viewName);
	}

	@Test
	public void doResetFailed() {
		// Given
		User user = userFactoryForTest.newRegisteredUser();
		RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
		when(userService.findUserByUserId(user.getUserId())).thenReturn(null);
		
		// When
		String viewName = userController.doReset(user, redirectAttributes);

		// Then
		verify(userService, never()).changeUserPassword(user, user.getUserPassword());
		Map<String, ?> redirectMap = redirectAttributes.getFlashAttributes();
		assertEquals("failed", redirectMap.get("reset"));
		assertEquals("redirect:/signin.html", viewName);
	}

	@Test
	public void doResetSuccess() {
		// Given
		User user = userFactoryForTest.newRegisteredUser();
		RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
		when(userService.findUserByUserId(user.getUserId())).thenReturn(user);
		
		// When
		String viewName = userController.doReset(user, redirectAttributes);

		// Then
		verify(userService).changeUserPassword(user, user.getUserPassword());
		Map<String, ?> redirectMap = redirectAttributes.getFlashAttributes();
		assertEquals("success", redirectMap.get("reset"));
		assertEquals("redirect:/signin.html", viewName);
	}

	@Test
	public void showPassword() {
		// When
		String viewName = userController.showPassword();

		// Then
		assertEquals("settings/password", viewName);
    }
	
	@Test
	public void changePasswordInvalid() {
		// Given
		User user = userFactoryForTest.newRegisteredUser();
		PasswordChange passwordChange = userFactoryForTest.newPasswordChange(user);
		RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
		when(userService.findUserByUserName(passwordChange.getUserName())).thenReturn(user);
		when(userService.validOldPassword(user, passwordChange.getOldPassword())).thenReturn(false);
		
		// When
		String viewName = userController.changePassword(passwordChange, redirectAttributes);

		// Then
		verify(userService, never()).changeUserPassword(user, passwordChange.getNewPassword());
		Map<String, ?> redirectMap = redirectAttributes.getFlashAttributes();
		assertTrue(Boolean.parseBoolean(redirectMap.get("invalid").toString()));
		assertEquals("redirect:/password/edit.html", viewName);
	}

	@Test
	public void changePasswordSuccess() {
		// Given
		User user = userFactoryForTest.newRegisteredUser();
		PasswordChange passwordChange = userFactoryForTest.newPasswordChange(user);
		RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
		when(userService.findUserByUserName(passwordChange.getUserName())).thenReturn(user);
		when(userService.validOldPassword(user, passwordChange.getOldPassword())).thenReturn(true);
		
		// When
		String viewName = userController.changePassword(passwordChange, redirectAttributes);

		// Then
		verify(userService).changeUserPassword(user, passwordChange.getNewPassword());
		Map<String, ?> redirectMap = redirectAttributes.getFlashAttributes();
		assertTrue(Boolean.parseBoolean(redirectMap.get("success").toString()));
		assertEquals("redirect:/password/edit.html", viewName);
	}
	
	@Test
	public void users() {
		// Given
		Model model = new ExtendedModelMap();
		List<User> list = new ArrayList<User>();
		when(userService.findAll()).thenReturn(list);
		
		// When
		String viewName = userController.users(model);
		
		// Then
		assertEquals("admin/users", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(list, modelMap.get("users"));
	}
	
	@Test
	public void userDetail() {
		// Given
		Model model = new ExtendedModelMap();
		User user = userFactoryForTest.newUser();
		String id = user.getUserId();
		when(userService.findUserByUserId(id)).thenReturn(user);
		
		// When
		String viewName = userController.userDetail(model, id);
		
		// Then
		assertEquals("admin/user-detail", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(user, modelMap.get("user"));
	}

	@Test
	public void account() {
		// Given
		Model model = new ExtendedModelMap();
		final User user = userFactoryForTest.newRegisteredUser();
		Principal principal = new Principal() {
			@Override
			public String getName() {
				return user.getUserName();
			}
		};
		String name = principal.getName();
		when(userService.findUserByUserName(name)).thenReturn(user);
		
		// When
		String viewName = userController.account(model, principal);
		
		// Then
		assertEquals("settings/account", viewName);
		Map<String,?> modelMap = model.asMap();
		assertEquals(user, modelMap.get("user"));
	}
	
	@Test
	public void removeUser() {
		// Given
		User user = userFactoryForTest.newUser();
		String id = user.getUserId();

		// When
		String viewName = userController.removeUser(id);

		// Then
		assertEquals("redirect:/users.html", viewName);
		verify(userService).deleteUser(id);
	}
}
