package com.magicalcyber.myfacebook.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.magicalcyber.myfacebook.web.form.RegisterForm;

@Controller
public class IndexController {

	@GetMapping("/")
	String index(Model model) {
		model.addAttribute("registerForm", new RegisterForm());
		return "index";
	}
}
