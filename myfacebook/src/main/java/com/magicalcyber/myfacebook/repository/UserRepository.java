package com.magicalcyber.myfacebook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.magicalcyber.myfacebook.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	User findByEmail(String email);

	List<User> findByNameContainingOrEmail(String name, String email);

}
