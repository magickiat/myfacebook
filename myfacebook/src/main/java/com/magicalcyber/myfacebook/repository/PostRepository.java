package com.magicalcyber.myfacebook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.magicalcyber.myfacebook.model.Post;
import com.magicalcyber.myfacebook.model.User;

public interface PostRepository extends JpaRepository<Post, Long> {
	List<Post> findByCreatedUserOrderByCreatedDateDesc(User user);
}
