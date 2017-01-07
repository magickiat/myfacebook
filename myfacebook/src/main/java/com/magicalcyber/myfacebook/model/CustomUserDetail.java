package com.magicalcyber.myfacebook.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class CustomUserDetail extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L;

	private String fullname;

	public CustomUserDetail(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}

	public CustomUserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities,
			String fullname) {
		super(username, password, authorities);
		this.fullname = fullname;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

}
