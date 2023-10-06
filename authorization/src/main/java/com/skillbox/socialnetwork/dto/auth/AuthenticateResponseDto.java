package com.skillbox.socialnetwork.dto.auth;

// DTO ответа на аутентификацию

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class AuthenticateResponseDto {

	@NonNull
	String accessToken;

	@NonNull
	String refreshToken;
}
