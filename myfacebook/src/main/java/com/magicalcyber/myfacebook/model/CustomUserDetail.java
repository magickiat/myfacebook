package com.magicalcyber.myfacebook.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class CustomUserDetail extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String fullname;

	public CustomUserDetail(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	public CustomUserDetail(Long userId, String username, String password,
			Collection<? extends GrantedAuthority> authorities, String fullname) {
		super(username, password, authorities);
		this.fullname = fullname;
		this.id = userId;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
