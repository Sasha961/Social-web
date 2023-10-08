package ru.skillbox.group39.socialnetwork.authorization.exception.exceptions;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class NotFoundException extends UsernameNotFoundException {

	public NotFoundException(String message) {
		super(message);
	}

}
