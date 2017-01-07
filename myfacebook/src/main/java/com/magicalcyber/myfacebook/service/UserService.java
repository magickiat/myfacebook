package com.magicalcyber.myfacebook.service;

import com.magicalcyber.myfacebook.constant.UserRole;
import com.magicalcyber.myfacebook.model.User;
import com.magicalcyber.myfacebook.web.form.RegisterForm;

public interface UserService {
	void create(RegisterForm form, UserRole userRole);

	User findByEmail(String email);
}
