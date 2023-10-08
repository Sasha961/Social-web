package ru.skillbox.group39.socialnetwork.authorization.service.Impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.skillbox.group39.socialnetwork.authorization.client.UsersClient;
import ru.skillbox.group39.socialnetwork.authorization.client.HealthChecker;
import ru.skillbox.group39.socialnetwork.authorization.dto.user.AccountDto;
import ru.skillbox.group39.socialnetwork.authorization.exception.exceptions.EmailIsBlankException;
import ru.skillbox.group39.socialnetwork.authorization.exception.exceptions.EmailNotUniqueException;
import ru.skillbox.group39.socialnetwork.authorization.security.model.Person;
import ru.skillbox.group39.socialnetwork.authorization.service.UserService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UsersClient usersClient;
	@Value("${users-service.host}")
	private String usersHost;
	@Value("${users-service.port}")
	private String usersPort;

	@Override
	public Person loadUserByUsername(String email) throws UsernameNotFoundException, EmailNotUniqueException {

		HealthChecker.checkHealthyService(usersHost, Integer.valueOf(usersPort));

		if (email == null | Objects.equals(email, "")) {
			throw new EmailIsBlankException("Email cant be blank or null");
		}

		AccountDto accountDto = usersClient.getUserDetails(email);


		return new Person(
				accountDto.getEmail(),
				accountDto.getPassword(),
				createListOfRoles(
						accountDto.getRoles())
						.stream()
						.map(SimpleGrantedAuthority::new)
						.collect(Collectors.toList()),
				accountDto.getId(),
				accountDto.getFirstName(),
				accountDto.getLastName());
	}

	public List<String> createListOfRoles(@NonNull String rolesAsString) {
		return Arrays
				.stream(rolesAsString.split(", "))
				.collect(Collectors.toList());
	}

}
