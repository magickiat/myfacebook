package com.magicalcyber.myfacebook.web.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.magicalcyber.myfacebook.model.CustomUserDetail;
import com.magicalcyber.myfacebook.service.UserService;
import com.magicalcyber.myfacebook.web.form.FriendForm;

@Controller
public class FriendController {

	private static final Logger log = LoggerFactory.getLogger(FriendController.class);

	@Autowired
	private UserService userService;

	@GetMapping("/friend")
	String friends(Model model) {

		// get current user
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		CustomUserDetail currentUser = (CustomUserDetail) auth.getPrincipal();

		FriendForm friendForm = new FriendForm();
		friendForm.setUserId(currentUser.getId());
		model.addAttribute("friendForm", friendForm);
		return "friend";
	}

	@GetMapping("/friend/search")
	String searchFriends(@Valid @ModelAttribute("friendForm") FriendForm friendForm, BindingResult result,
			Model model) {
		if (!result.hasErrors()) {
			model.addAttribute("users", userService.searchFriend(friendForm));
		}
		return "friend";
	}

	@PostMapping("/friend/add")
	String addFriend(@RequestParam("userId") Long userId, Model model) {
		log.info("request friend id: " + userId);

		userService.addFriend(userId);
		return "redirect:/";
	}

	@PostMapping("/friend/remove")
	String removeFriend(@RequestParam("userId") Long userId, Model model) {
		log.info("remove friend id: " + userId);

		userService.removeFriend(userId);
		return "redirect:/";
	}
}
