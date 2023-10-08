package ru.skillbox.group39.socialnetwork.authorization.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.skillbox.group39.socialnetwork.authorization.dto.user.AccountDto;
import ru.skillbox.group39.socialnetwork.authorization.security.jwt.JwtTokenUtils;
import ru.skillbox.group39.socialnetwork.authorization.security.model.Person;
import ru.skillbox.group39.socialnetwork.authorization.service.Impl.AuthServiceImpl;
import ru.skillbox.group39.socialnetwork.authorization.service.Impl.UserServiceImpl;
import ru.skillbox.group39.socialnetwork.authorization.dto.auth.AuthenticateDto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Artem Lebedev | 03/09/2023 - 23:00
 */

@SpringBootTest(properties = "application-prod.yaml", webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
public class AuthServiceTest {

	@Autowired
	private JwtTokenUtils jwtTokenUtils;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserServiceImpl userService;

	@Autowired
	private AuthServiceImpl authService;


	@Before
	public void setUp() {
		Person person = new Person(
				"foo@bar.com",
				"raboof",
				new ArrayList<SimpleGrantedAuthority>(),
				7L,
				"Foo",
				"Bar");
	}

	AccountDto accountDto = new AccountDto(
			"Foo",
			"Bar",
			"foo@bar.com",
			"$2a$10$ZY9xBwLO8HYBoKj9x/lORutRNj5BWUjSWTgvc.xxvGELrZBR2z08C",
			"USER_ROLE");

	AuthenticateDto authenticateDto = new AuthenticateDto(
			"foo@bar.com",
			"raboof");
	AuthenticateDto authenticateDtoBadRequest = new AuthenticateDto(
			"foo@bar.com",
			"");

	String rolesAsString = "USER_ROLE, ADMIN_ROLE, SUPERVISOR_ROLE";
	List<String> listOfRoles = List.of("USER_ROLE", "ADMIN_ROLE", "SUPERVISOR_ROLE");

	@Test
	public void loginTest_Successful() {
		Person person = new Person(
				"foo@bar.com",
				"raboof",
				new ArrayList<SimpleGrantedAuthority>(),
				7L,
				"Foo",
				"Bar");

		ResponseEntity<?> loginResponse = authService.login(authenticateDto);
		assertNotNull(loginResponse);
		assertEquals(HttpStatus.OK, loginResponse.getStatusCode());
	}

	@Test
	public void loginTest_BadRequest() {

		ResponseEntity<?> loginResponse = authService.login(authenticateDtoBadRequest);
		assertNotNull(loginResponse);
		assertEquals(HttpStatus.BAD_REQUEST, loginResponse.getStatusCode());
	}
//
//	@Test
//	public void createListOfRolesTest() {
//		List<String> list = userService.createListOfRoles(rolesAsString);
//		assertNotNull(list);
//		assertEquals(listOfRoles, list);
//	}



}
