package com.skillbox.socialnetwork.dto;

//Дто для запроса на регистрацию

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationDto {

	String id;
	Boolean isDeleted;
	String firstName;
	String lastName;
	String email;
	String password;
	String confirmPassword;
	String captchaCode;
	String captchaSecret;

	public RegistrationDto(String firstName,
	                       String lastName,
	                       String email,
	                       String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}
}
