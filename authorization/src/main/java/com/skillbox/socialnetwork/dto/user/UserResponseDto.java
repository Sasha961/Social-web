package com.skillbox.socialnetwork.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserResponseDto {

	private AccountSecureDto accountSecureDto;
	private Boolean isExist;
}
