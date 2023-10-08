package ru.skillbox.group39.socialnetwork.authorization.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticateResponseDto {

	private String accessToken;
	private String refreshToken;
}
