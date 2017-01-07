package com.magicalcyber.myfacebook.web.validator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.magicalcyber.myfacebook.constant.UserRole;
import com.magicalcyber.myfacebook.service.UserService;
import com.magicalcyber.myfacebook.web.form.RegisterForm;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Sql(executionPhase = ExecutionPhase.BEFORE_TEST_METHOD, scripts = { "classpath:before.sql" })
@Transactional
public class RegisterValidatorTest {

	private static final String EMAIL = "mm@gmail.com";
	
	@Autowired
	private Validator registerValidator;
	
	@Autowired
	private UserService userService;

	@Test
	public void validatePassword_notSameAsRePassword_mustError() {
		RegisterForm form = new RegisterForm();
		form.setPassword("ABC");
		form.setRePassword("CDE");

		Errors errors = new BeanPropertyBindingResult(form, "registerForm");
		registerValidator.validate(form, errors);
		assertTrue(errors.hasErrors());
	}

	@Test
	public void validatePassword_sameAsRePassword_mustPassed() {
		RegisterForm form = new RegisterForm();
		form.setPassword("ABC");
		form.setRePassword("ABC");

		Errors errors = new BeanPropertyBindingResult(form, "registerForm");
		registerValidator.validate(form, errors);
		assertFalse(errors.hasErrors());
	}

	@Test
	public void validateEmail_notExist_mustPassed() {
		RegisterForm form = new RegisterForm();
		form.setPassword("ABC");
		form.setRePassword("ABC");
		form.setEmail(EMAIL);

		Errors errors = new BeanPropertyBindingResult(form, "registerForm");
		registerValidator.validate(form, errors);
		assertFalse(errors.hasErrors());
	}
	
	@Test
	public void validateEmail_exist_mustError() {

		// Create first user that registered email
		RegisterForm form = new RegisterForm();
		form.setPassword("ABC");
		form.setRePassword("ABC");
		form.setEmail(EMAIL);
		userService.create(form, UserRole.ROLE_USER);
		
		// validate
		Errors errors = new BeanPropertyBindingResult(form, "registerForm");
		registerValidator.validate(form, errors);
		assertTrue(errors.hasErrors());
	}
}
