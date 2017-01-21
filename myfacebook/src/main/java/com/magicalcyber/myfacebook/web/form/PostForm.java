package com.magicalcyber.myfacebook.web.form;

import org.hibernate.validator.constraints.NotEmpty;

public class PostForm {

	private Long userId;

	@NotEmpty
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "PostForm [userId=" + userId + ", content=" + content + "]";
	}

	
}
