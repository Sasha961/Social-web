package com.skillbox.socialnetwork.dto.user;

import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {

	private Long userId;

	public Long getId() {
		return userId;
	}

	public void setId(Long id) {
		this.userId = id;
	}

	public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, Long userId) {
		super(username, password, authorities);
		setId(userId);
	}
}
