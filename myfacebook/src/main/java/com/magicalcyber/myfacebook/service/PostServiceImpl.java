package com.magicalcyber.myfacebook.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magicalcyber.myfacebook.model.Comment;
import com.magicalcyber.myfacebook.model.Post;
import com.magicalcyber.myfacebook.model.User;
import com.magicalcyber.myfacebook.repository.PostRepository;
import com.magicalcyber.myfacebook.repository.UserRepository;
import com.magicalcyber.myfacebook.web.form.CommentForm;
import com.magicalcyber.myfacebook.web.form.PostForm;

@Service("postService")
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public void save(PostForm postForm) {
		User currentUser = userRepository.getOne(postForm.getUserId());
		Post post = new Post();
		post.setCreatedUser(currentUser);
		post.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		post.setContent(postForm.getContent());
		postRepository.save(post);
	}

	@Override
	public List<Post> listPost(Long userId) {

		// get current user
		User currentUser = userRepository.findOne(userId);

		HashSet<User> users = new HashSet<>();
		if (currentUser.getFriends() != null && !currentUser.getFriends().isEmpty()) {
			users = new HashSet<>(currentUser.getFriends());
		}
		users.add(currentUser);

		return postRepository.findByCreatedUserInOrderByCreatedDateDesc(users);
	}

	@Override
	public void saveComment(CommentForm commentForm) {
		Post post = postRepository.findOne(commentForm.getPostId());
		if (post == null) {
			throw new IllegalArgumentException("Not found this post");
		} else {
			if (post.getComments() == null) {
				post.setComments(new ArrayList<>());
			}

			User commentUser = userRepository.findOne(commentForm.getUserId());

			Comment comment = new Comment();
			comment.setCreatedUser(commentUser);
			comment.setContent(commentForm.getContent());
			comment.setCreatedDate(new Timestamp(System.currentTimeMillis()));

			post.getComments().add(comment);
			postRepository.save(post);
		}
	}

}
