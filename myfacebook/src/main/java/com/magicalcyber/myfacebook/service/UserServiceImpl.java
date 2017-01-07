package com.magicalcyber.myfacebook.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.magicalcyber.myfacebook.constant.UserRole;
import com.magicalcyber.myfacebook.model.User;
import com.magicalcyber.myfacebook.repository.RoleRepository;
import com.magicalcyber.myfacebook.repository.UserRepository;
import com.magicalcyber.myfacebook.web.form.RegisterForm;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void create(RegisterForm form, UserRole userRole) {
		User user = new User();
		user.setName(form.getName());
		user.setEmail(form.getEmail());
		user.setPassword(passwordEncoder.encode(form.getPassword()));
		user.setRole(roleRepository.findOne(userRole.name()));
		user.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		userRepository.save(user);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}
