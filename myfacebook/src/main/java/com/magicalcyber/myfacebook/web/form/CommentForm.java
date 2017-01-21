package com.magicalcyber.myfacebook.web.form;

import org.hibernate.validator.constraints.NotEmpty;

public class CommentForm {
	private Long postId;
	private Long userId;

	@NotEmpty
	private String content;

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "CommentForm [postId=" + postId + ", userId=" + userId + ", content=" + content + "]";
	}

}
