package com.skillbox.socialnetwork.exceptions;

import org.springframework.security.authentication.BadCredentialsException;

public class UnauthorizedException extends BadCredentialsException {

	public UnauthorizedException(String message) {
		super(message);
	}
}
