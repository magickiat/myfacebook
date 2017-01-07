package com.magicalcyber.myfacebook.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.magicalcyber.myfacebook.model.User;
import com.magicalcyber.myfacebook.repository.UserRepository;
import com.magicalcyber.myfacebook.web.form.RegisterForm;

@Component
public class RegisterValidator implements Validator {

	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return RegisterForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		RegisterForm form = (RegisterForm) target;

		// Check Password and Re-Password must match.
		if (!form.getPassword().equals(form.getRePassword())) {
			errors.rejectValue("password", null, "Password not same as Re-Password");
			errors.rejectValue("rePassword", null, "Re-Password not same as Password");
		}

		// Check Email is not registered.
		User user = userRepository.findByEmail(form.getEmail());
		if (user != null) {
			errors.rejectValue("email", null, "This email was registered");
		}
	}

}
