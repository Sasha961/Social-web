package com.skillbox.socialnetwork.services;

import com.skillbox.socialnetwork.dto.RegistrationDto;

public interface PasswordRecoveryService {
	RegistrationDto getRequestForEmail(String email);
}
