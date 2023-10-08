package ru.skillbox.group39.socialnetwork.authorization.dto.auth;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto {

	private String id;
	private Boolean isDeleted;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String confirmPassword;
	@Nullable
	private String captchaCode;
	private String captchaSecret;

	public RegistrationDto(final String firstName,
	                       final String lastName,
	                       final String email,
	                       final String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	@Override
	public String toString() {
		return "\nRegistrationDto" +
				"\n{\n" +
				"   First name       -  '" + firstName + "'\n" +
				"   Last name        -  '" + lastName + "'\n" +
				"   Email            -  '" + email + "'\n" +
				"   Password         -  '" + password + "'\n" +
				"   Confirm password -  '" + confirmPassword + "'\n" +
				"   Captcha code     -  '" + captchaCode + "'\n" +
				"   Captcha secret   -  '" + captchaSecret + "'\n" +
				"}\n";
	}
}
