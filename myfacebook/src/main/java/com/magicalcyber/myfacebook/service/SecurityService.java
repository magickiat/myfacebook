package com.magicalcyber.myfacebook.service;

public interface SecurityService {
	String findCurrentUsername();
	void autologin(String email, String password);
}
