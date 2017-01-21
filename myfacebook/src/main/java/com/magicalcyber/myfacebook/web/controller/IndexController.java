package com.magicalcyber.myfacebook.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.magicalcyber.myfacebook.model.CustomUserDetail;
import com.magicalcyber.myfacebook.service.PostService;
import com.magicalcyber.myfacebook.web.form.PostForm;
import com.magicalcyber.myfacebook.web.form.RegisterForm;

@Controller
public class IndexController {

	@Autowired
	private PostService postService;

	@GetMapping("/")
	String index(Model model) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || (auth != null && auth instanceof AnonymousAuthenticationToken)) {
			model.addAttribute("registerForm", new RegisterForm());
			return "index";
		} else {
			// get current user
			CustomUserDetail currentUser = (CustomUserDetail) auth.getPrincipal();

			// create post model
			PostForm form = new PostForm();
			form.setUserId(currentUser.getId());
			model.addAttribute("postForm", form);

			// list post
			model.addAttribute("posts", postService.listPost(currentUser.getId()));

			return "hello";
		}

	}
}
