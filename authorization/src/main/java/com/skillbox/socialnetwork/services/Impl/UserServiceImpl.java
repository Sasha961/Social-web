package com.skillbox.socialnetwork.services.Impl;

import com.skillbox.socialnetwork.client.ExternalClient;
import com.skillbox.socialnetwork.dto.user.UserResponseDto;
import com.skillbox.socialnetwork.dto.user.CustomUserDetails;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements com.skillbox.socialnetwork.services.UserService {

	private final ExternalClient externalClient;

	@Override
	public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		UserResponseDto userResponseDto = externalClient.getUserDetails(email);

		if (userResponseDto.getIsExist()){
			return new CustomUserDetails(
					userResponseDto.getAccountSecureDto().getEmail(),
					userResponseDto.getAccountSecureDto().getPassword(),
					createListOfRoles(
							userResponseDto.getAccountSecureDto().getRoles())
							.stream()
							.map(SimpleGrantedAuthority::new)
							.collect(Collectors.toList()),
					userResponseDto.getAccountSecureDto().getId());
		} else {
			return null;
		}
	}

	private List<String> createListOfRoles(@NonNull String rolesAsString) {
		return Arrays
				.stream(rolesAsString.split(", "))
				.collect(Collectors.toList());
	}

}
