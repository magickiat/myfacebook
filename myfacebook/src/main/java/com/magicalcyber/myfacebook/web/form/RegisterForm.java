package com.magicalcyber.myfacebook.web.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

public class RegisterForm {

	@Length(min = 5, max = 32)
	@Email
	private String email;

	@Length(min = 5, max = 32)
	private String name;

	@Length(min = 5, max = 32)
	private String password;

	@Length(min = 5, max = 32)
	private String rePassword;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRePassword() {
		return rePassword;
	}

	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "RegisterForm [email=" + email + ", name=" + name + ", password=" + password + ", rePassword="
				+ rePassword + "]";
	}

}
