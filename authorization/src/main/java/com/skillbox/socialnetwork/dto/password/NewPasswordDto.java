package com.skillbox.socialnetwork.dto.password;

// DTO для установки нового пароля

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewPasswordDto {

	@NonNull
	String password;
}
