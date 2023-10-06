package com.skillbox.socialnetwork.dto.password;

// DTO для заявки на смену пароля

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordRecoveryDto {

	@NonNull
	String email;
}
