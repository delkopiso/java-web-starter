package com.kossyuzokwe.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.kossyuzokwe.model.User;
import com.kossyuzokwe.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@ModelAttribute("user")
	public User constructUser() {
		return new User();
	}

	@RequestMapping("/register")
	public String showRegister() {
		return "auth/signup";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String doRegister(@Valid @ModelAttribute("user") User user,
			BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "auth/signup";
		}
		userService.save(user);
		redirectAttributes.addFlashAttribute("success", true);
		return "redirect:/register.html";
	}
	
	@RequestMapping("/register/available")
	@ResponseBody
	public String available(@RequestParam String username) {
		Boolean available = userService.findOneByUserName(username) == null;
		return available.toString();
	}

	@RequestMapping("/login")
	public String login() {
		return "auth/signin";
	}

	@RequestMapping("/users")
	public String users(Model model) {
		model.addAttribute("users", userService.findAll());
		return "admin/users";
	}

	@RequestMapping("/users/{id}")
	public String userDetail(Model model, @PathVariable String id) {
		model.addAttribute("user", userService.findOneByUserId(id));
		return "admin/user-detail";
	}

	@RequestMapping(value="/account", method=RequestMethod.GET)
	public String account(Model model, Principal principal) {
		String name = principal.getName();
		model.addAttribute("user", userService.findOneByUserName(name));
		return "settings/account";
	}

	@RequestMapping("/users/remove/{id}")
	public String removeUser(@PathVariable String id) {
		userService.delete(id);
		return "redirect:/users.html";
	}
	
/*
 * --------------------------------------------------------------------------------------
 * ------------------------------------- NEW REST METHODS -------------------------------
 * --------------------------------------------------------------------------------------
	
	@RequestMapping(value="/users",method=RequestMethod.GET)
	public String listUsers(){
		return null;
	}
	
	@RequestMapping(value="/users/{id}",method=RequestMethod.GET)
	public String getUser(){
		return null;
	}
	
	@RequestMapping(value="/users/{id}",method=RequestMethod.PUT)
	public String updateUser(){
		return null;
	}
	
	@RequestMapping(value="/users/{id}",method=RequestMethod.DELETE)
	public String deleteUser(){
		return null;
	}
	
	@RequestMapping(value="/users/me",method=RequestMethod.GET)
	public String getMe(){
		return null;
	}
	
	@RequestMapping(value="/users/password",method=RequestMethod.POST)
	public String changePassword(){
		return null;
	}
	
	@RequestMapping(value="/auth/forgot",method=RequestMethod.POST)
	public String forgotPassword(){
		return null;
	}
	
	@RequestMapping(value="/auth/reset/{token}",method=RequestMethod.GET)
	public String validateResetToken(){
		return null;
	}
	
	@RequestMapping(value="/auth/reset/{token}",method=RequestMethod.POST)
	public String resetPassword(){
		return null;
	}
	
	@RequestMapping(value="/auth/signup",method=RequestMethod.POST)
	public String signUp(){
		return null;
	}
	
	@RequestMapping(value="/auth/signin",method=RequestMethod.POST)
	public String signIn(){
		return null;
	}
	*/
}
