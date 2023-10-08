package ru.skillbox.group39.socialnetwork.authorization.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountSecureDto {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String roles;

	public AccountSecureDto(String firstName, String lastName, String email, String password, String roles) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.roles = roles;
	}
}
