package com.magicalcyber.myfacebook.service;

import java.util.List;

import com.magicalcyber.myfacebook.model.Post;
import com.magicalcyber.myfacebook.web.form.CommentForm;
import com.magicalcyber.myfacebook.web.form.PostForm;

public interface PostService {
	void save(PostForm postForm);

	void saveComment(CommentForm commentForm);

	List<Post> listPost(Long userId);
}
