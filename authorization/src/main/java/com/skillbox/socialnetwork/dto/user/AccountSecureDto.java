package com.skillbox.socialnetwork.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountSecureDto {
	Long id;
	Boolean isDeleted;
	String firstName;
	String lastName;
	String email;
	String password;
	String roles;
}
