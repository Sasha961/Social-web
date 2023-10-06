package com.skillbox.socialnetwork.services.Impl;

import com.skillbox.socialnetwork.dto.RegistrationDto;
import com.skillbox.socialnetwork.services.PasswordRecoveryService;
import com.skillbox.socialnetwork.utils.Randomizer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Getter
@RequiredArgsConstructor
public class PasswordRecoveryServiceImpl implements PasswordRecoveryService {

	private final Randomizer randomizer;

	@Override
	public RegistrationDto getRequestForEmail(String email) {
		return getRegistrationInfo(email);
	}

	private RegistrationDto getRegistrationInfo(String email){

		return RegistrationDto.builder()
				.id(randomizer.getRandomString(15, true, true))
				.password(randomizer.getRandomString(10, true, true))
				.confirmPassword(randomizer.getRandomString(10, true, true))
				.captchaCode(randomizer.getRandomString(10, true, true))
				.captchaSecret(randomizer.getRandomString(10, true, true))
				.email(email)
				.firstName(randomizer.getRandomString(10, true, false))
				.lastName(randomizer.getRandomString(10, true, false))
				.isDeleted(true)
				.build();
	}
}
