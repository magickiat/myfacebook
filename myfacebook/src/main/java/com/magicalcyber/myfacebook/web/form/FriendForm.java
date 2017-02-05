package com.magicalcyber.myfacebook.web.form;

import org.hibernate.validator.constraints.NotEmpty;

public class FriendForm {

	private Long userId;

	@NotEmpty
	private String keyword;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
