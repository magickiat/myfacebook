package com.magicalcyber.myfacebook.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.magicalcyber.myfacebook.constant.UserRole;
import com.magicalcyber.myfacebook.service.UserService;
import com.magicalcyber.myfacebook.web.form.RegisterForm;

@Controller
public class RegisterController {

	private static final Logger log = LoggerFactory.getLogger(RegisterController.class);

	@Autowired
	private Validator registerValidator;
	
	@Autowired
	private UserService userService;

	@PostMapping("/register")
	String register(@Valid @ModelAttribute("registerForm") RegisterForm registerForm,
			BindingResult result, Model model) {
		log.info(registerForm.toString());

		registerValidator.validate(registerForm, result);
		if (result.hasErrors()) {
			List<ObjectError> allErrors = result.getAllErrors();
			for (ObjectError objectError : allErrors) {
				log.error("\t*** " + objectError);
			}
			return "index";
		} else {
			userService.create(registerForm, UserRole.ROLE_USER);
			return "redirect:login";
		}

	}
}
