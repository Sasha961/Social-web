package com.skillbox.socialnetwork.dto.auth;

// DTO аутентификации

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticateDto {

	@NonNull
	String email;

	@NonNull
	String password;
}
