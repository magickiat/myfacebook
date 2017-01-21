package com.magicalcyber.myfacebook.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.magicalcyber.myfacebook.model.CustomUserDetail;
import com.magicalcyber.myfacebook.model.User;
import com.magicalcyber.myfacebook.repository.UserRepository;

@Service("userDetailService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		User user = userRepository.findByEmail(email);

		if (user == null) {
			throw new UsernameNotFoundException(email);
		}

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole().getName()));

		return new CustomUserDetail(user.getId(), email, user.getPassword(), grantedAuthorities, user.getName());
	}

}
