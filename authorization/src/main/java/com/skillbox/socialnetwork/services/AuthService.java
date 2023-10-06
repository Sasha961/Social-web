package com.skillbox.socialnetwork.services;

import com.skillbox.socialnetwork.dto.RegistrationDto;
import com.skillbox.socialnetwork.jwt.dto.JwtRequest;

public interface AuthService {
	Object login(JwtRequest jwtRequest);
	Object refreshTokens(JwtRequest jwtRequest);
	void createUser(RegistrationDto registrationDto);
//	ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest);
//	ResponseEntity<?> createNewUser(@RequestBody RegistrationDto registrationDto);
}
