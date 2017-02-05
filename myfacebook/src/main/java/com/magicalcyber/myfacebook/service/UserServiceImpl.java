package com.magicalcyber.myfacebook.service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.magicalcyber.myfacebook.constant.UserRole;
import com.magicalcyber.myfacebook.dto.FriendDto;
import com.magicalcyber.myfacebook.model.CustomUserDetail;
import com.magicalcyber.myfacebook.model.User;
import com.magicalcyber.myfacebook.repository.RoleRepository;
import com.magicalcyber.myfacebook.repository.UserRepository;
import com.magicalcyber.myfacebook.web.form.FriendForm;
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

	@Override
	public Set<FriendDto> searchFriend(FriendForm friendForm) {
		// set cache for check already friend
		HashMap<Long, User> friendCache = new HashMap<>();
		User currentUser = userRepository.findOne(friendForm.getUserId());
		Set<User> currentFriends = currentUser.getFriends();
		if (currentFriends != null && !currentFriends.isEmpty()) {
			for (User friend : currentFriends) {
				friendCache.put(friend.getId(), friend);
			}
		}

		// set user and check already friend
		Set<FriendDto> userList = new HashSet<>();

		List<User> users = userRepository.findByNameContainingOrEmail(friendForm.getKeyword(), friendForm.getKeyword());
		if (users != null && !users.isEmpty()) {
			for (User user : users) {
				FriendDto friend = new FriendDto();
				friend.setUserId(user.getId());
				friend.setName(user.getName());
				friend.setFriend(friendCache.containsKey(user.getId()));
				userList.add(friend);
			}
		}
		return userList;
	}

	@Override
	public void addFriend(Long userId) {
		// get current user
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetail userDetail = (CustomUserDetail) auth.getPrincipal();
		User currentUser = userRepository.findOne(userDetail.getId());

		// add current friend
		User requestedFriend = userRepository.findOne(userId);
		if (currentUser.getFriends() == null) {
			currentUser.setFriends(new HashSet<>());
		}
		currentUser.getFriends().add(requestedFriend);
		userRepository.save(currentUser);

		// add requested friend
		if (requestedFriend.getFriends() == null) {
			requestedFriend.setFriends(new HashSet<>());
		}
		requestedFriend.getFriends().add(currentUser);
		userRepository.save(requestedFriend);
	}

	@Override
	public void addFriend(Long userId, Long friendId) {
		// get current user
		User currentUser = userRepository.findOne(userId);

		// add current friend
		User requestedFriend = userRepository.findOne(friendId);
		if (currentUser.getFriends() == null) {
			currentUser.setFriends(new HashSet<>());
		}
		currentUser.getFriends().add(requestedFriend);
		userRepository.save(currentUser);

		// add requested friend
		if (requestedFriend.getFriends() == null) {
			requestedFriend.setFriends(new HashSet<>());
		}
		requestedFriend.getFriends().add(currentUser);
		userRepository.save(requestedFriend);
	}

	@Override
	public void removeFriend(Long userId) {
		// get current user
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetail userDetail = (CustomUserDetail) auth.getPrincipal();
		User currentUser = userRepository.findOne(userDetail.getId());

		// remove current friend
		User requestedFriend = userRepository.findOne(userId);
		if (currentUser.getFriends() != null) {
			currentUser.getFriends().remove(requestedFriend);
		}
		userRepository.save(currentUser);

		// remove requested friend
		if (requestedFriend.getFriends() != null) {
			requestedFriend.getFriends().remove(currentUser);
		}
		userRepository.save(requestedFriend);
	}

}
