package com.magicalcyber.myfacebook.dto;

public class FriendDto {

	private Long userId;
	private String name;
	private boolean friend;

	public FriendDto() {
	}

	public FriendDto(Long userId, String name, boolean friend) {
		this.userId = userId;
		this.name = name;
		this.friend = friend;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isFriend() {
		return friend;
	}

	public void setFriend(boolean friend) {
		this.friend = friend;
	}

}
