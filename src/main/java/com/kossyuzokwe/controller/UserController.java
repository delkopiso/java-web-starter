package com.kossyuzokwe.controller;

import java.security.Principal;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kossyuzokwe.event.OnRegistrationCompleteEvent;
import com.kossyuzokwe.event.OnResetPasswordEvent;
import com.kossyuzokwe.event.OnReverifyAccountEvent;
import com.kossyuzokwe.model.PasswordResetToken;
import com.kossyuzokwe.model.User;
import com.kossyuzokwe.model.VerificationToken;
import com.kossyuzokwe.service.EmailService;
import com.kossyuzokwe.service.UserService;
import com.kossyuzokwe.util.Helpers;

@Controller
public class UserController {
	
	Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

	@ModelAttribute("user")
	public User constructUser() {
		return new User();
	}
	
	@ModelAttribute("passwordChange")
	public PasswordChange constructPasswordChange() {
		return new PasswordChange();
	}

	@RequestMapping("/signup")
	public String showRegister() {
		return "auth/signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String doRegister(@Valid @ModelAttribute("user") User user, HttpServletRequest request,
			BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "auth/signup";
		}
		User registeredUser = userService.registerNewUserAccount(user);
		String appUrl = Helpers.getURL(request);
		eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registeredUser, appUrl));
		redirectAttributes.addFlashAttribute("success", true);
		return "redirect:/signup.html";
	}

	@RequestMapping("/signup/available-name")
	@ResponseBody
	public String availableName(@RequestParam String username) {
		Boolean available = userService.findUserByUserName(username) == null;
		return available.toString();
	}

	@RequestMapping("/signup/email-exists")
	@ResponseBody
	public String emailExists(@RequestParam String email) {
		Boolean available = !userService.emailExists(email);
		return available.toString();
	}
	
	@RequestMapping("/verify/{token}")
	public String verify(Model model, @PathVariable String token) {
		VerificationToken verificationToken = userService.getVerificationToken(token);
		if (verificationToken == null) {
            model.addAttribute("invalid", true);
            return "auth/failed";
        }
		
        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            model.addAttribute("expired", true);
            model.addAttribute("token", token);
            return "auth/failed";
        }
        
        user.setUserEnabled(true);
        userService.save(user);
        model.addAttribute("verified", true);
		return "settings/account";
	}
	
	@RequestMapping("/reverify/{existingToken}")
	public String reverify(Model model, HttpServletRequest request,
			@PathVariable String existingToken) {
		VerificationToken newToken = userService.regenerateVerificationToken(existingToken);
		User user = userService.findUserByVerificationToken(newToken.getToken());
		String appUrl = Helpers.getURL(request);
		eventPublisher.publishEvent(new OnReverifyAccountEvent(user, appUrl));
		model.addAttribute("resent", true);
		return "auth/failed";
	}

	@RequestMapping("/signin")
	public String login() {
		return "auth/signin";
	}

	@RequestMapping("/forgot")
	public String showForgot() {
		return "password/forgot";
	}

	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public String doForgot(@ModelAttribute("user") User user, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		User existingUser = userService.findUserByUserEmail(user.getUserEmail());
		boolean userExists = existingUser != null;
		if (userExists) {
			String appUrl = Helpers.getURL(request);
			eventPublisher.publishEvent(new OnResetPasswordEvent(existingUser, appUrl));
		}
		redirectAttributes.addFlashAttribute("exists", userExists);
		return "redirect:/forgot.html";
	}
	
	@RequestMapping("/reset/{id}/{token}")
	public String showReset(Model model, @PathVariable String id, @PathVariable String token) {
    	PasswordResetToken passToken = userService.getPasswordResetToken(token);
    	if (passToken != null) {
        	User requestUser = userService.findUserByUserId(id);
        	User tokenUser = passToken.getUser();
    		Calendar cal = Calendar.getInstance();
            if ((passToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
                model.addAttribute("expired", true);
            } else if (!tokenUser.equals(requestUser)) {
                model.addAttribute("invalid", true);
            } else {
            	model.addAttribute("id", id);
            }
    	} else {
            model.addAttribute("invalid", true);
    	}
        return "password/reset";
	}

    @RequestMapping(value = "/reset", method = RequestMethod.POST)
    public String doReset(@ModelAttribute("user") User user,
			RedirectAttributes redirectAttributes) {
    	User existingUser = userService.findUserByUserId(user.getUserId());
    	if (existingUser == null) {
    		redirectAttributes.addFlashAttribute("reset", "failed");
    	} else {
        	userService.changeUserPassword(existingUser, user.getUserPassword());
    		redirectAttributes.addFlashAttribute("reset", "success");
    	}
    	return "redirect:/signin.html";
    }

    @RequestMapping("/password/edit")
    public String showPassword() {
    	return "settings/password";
    }

    @RequestMapping(value = "/password/edit", method = RequestMethod.POST)
    @PreAuthorize("#passwordChange.userName == authentication.name or hasRole('ROLE_ADMIN')")
    public String changePassword(@ModelAttribute("passwordChange") PasswordChange passwordChange,
			RedirectAttributes redirectAttributes) {
    	User user = userService.findUserByUserName(passwordChange.getUserName());
    	if (!userService.validOldPassword(user, passwordChange.getOldPassword())) {
        	redirectAttributes.addFlashAttribute("invalid", true);
    	} else {
    		userService.changeUserPassword(user, passwordChange.getNewPassword());
        	redirectAttributes.addFlashAttribute("success", true);
    	}
    	return "redirect:/password/edit.html";
    }

	@RequestMapping("/users")
	public String users(Model model) {
		model.addAttribute("users", userService.findAll());
		return "admin/users";
	}

	@RequestMapping("/users/{id}")
	public String userDetail(Model model, @PathVariable String id) {
		model.addAttribute("user", userService.findUserByUserId(id));
		return "admin/user-detail";
	}

	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public String account(Model model, Principal principal) {
		String name = principal.getName();
		model.addAttribute("user", userService.findUserByUserName(name));
		return "settings/account";
	}

	@RequestMapping("/users/remove/{id}")
	public String removeUser(@PathVariable String id) {
		userService.deleteUser(id);
		return "redirect:/users.html";
	}

	@SuppressWarnings("unused")
	private class PasswordChange {
		private String userName;
		private String oldPassword;
		private String newPassword;

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public String getOldPassword() {
			return oldPassword;
		}

		public void setOldPassword(String oldPassword) {
			this.oldPassword = oldPassword;
		}

		public String getNewPassword() {
			return newPassword;
		}

		public void setNewPassword(String newPassword) {
			this.newPassword = newPassword;
		}
	}

}