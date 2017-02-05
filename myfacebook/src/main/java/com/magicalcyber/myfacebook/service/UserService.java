package com.magicalcyber.myfacebook.service;

import java.util.Set;

import com.magicalcyber.myfacebook.constant.UserRole;
import com.magicalcyber.myfacebook.dto.FriendDto;
import com.magicalcyber.myfacebook.model.User;
import com.magicalcyber.myfacebook.web.form.FriendForm;
import com.magicalcyber.myfacebook.web.form.RegisterForm;

public interface UserService {
	void create(RegisterForm form, UserRole userRole);

	User findByEmail(String email);

	Set<FriendDto> searchFriend(FriendForm friendForm);

	void addFriend(Long userId);

	void removeFriend(Long userId);

	void addFriend(Long userId, Long friendId);

}
