package ru.skillbox.group39.socialnetwork.authorization.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import ru.skillbox.group39.socialnetwork.authorization.client.UsersClient;
import ru.skillbox.group39.socialnetwork.authorization.controller.Impl.AuthControllerImpl;
import ru.skillbox.group39.socialnetwork.authorization.dto.auth.AuthenticateDto;
import ru.skillbox.group39.socialnetwork.authorization.dto.auth.AuthenticateResponseDto;
import ru.skillbox.group39.socialnetwork.authorization.dto.auth.RegistrationDto;
import ru.skillbox.group39.socialnetwork.authorization.dto.user.AccountDto;
import ru.skillbox.group39.socialnetwork.authorization.dto.user.AccountSecureDto;
import ru.skillbox.group39.socialnetwork.authorization.kafka.KafkaProducer;
import ru.skillbox.group39.socialnetwork.authorization.security.jwt.JwtTokenUtils;
import ru.skillbox.group39.socialnetwork.authorization.security.model.Person;
import ru.skillbox.group39.socialnetwork.authorization.service.AuthService;
import ru.skillbox.group39.socialnetwork.authorization.service.Impl.UserServiceImpl;
import ru.skillbox.group39.socialnetwork.authorization.dto.password.NewPasswordDto;
import ru.skillbox.group39.socialnetwork.authorization.dto.password.PasswordRecoveryDto;

import java.time.Duration;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Artem Lebedev | 02/09/2023 - 22:40
 * <p>
 * <p>
 * Responce from <b>users</b><p>
 * {<p>
 * "headers": {},<p>
 * "body": {<p>
 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"id": 58,<p>
 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"firstName": "foo",<p>
 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"lastName": "bar",<p>
 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"email": "foo@bar.com",<p>
 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"password": "$2a$10$ZY9xBwLO8HYBoKj9x/lORutRNj5BWUjSWTgvc.xxvGELrZBR2z08C",<p>
 * &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"roles": "ROLE_USER"},<p>
 * "statusCode": "OK",<p>
 * "statusCodeValue": 200<p>
 * }
 */

@AutoConfigureMockMvc(printOnlyOnFailure = false)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthorizationControllerTestIT {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthService authService;

	@MockBean
	private UsersClient usersClient;

	@Autowired
	private final AuthControllerImpl authController = new AuthControllerImpl(
			this.authService,
			this.userService,
			this.jwtTokenUtils,
			this.authenticationManager,
			this.kafkaProducer);

	@MockBean
	private KafkaProducer kafkaProducer;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private JwtTokenUtils jwtTokenUtils;

	@MockBean
	private UserServiceImpl userService;

//	@Test
//	void captcha_Accepted() throws Exception {
//		var request = get("/api/v1/auth/captcha");
//
//		given(authController.captcha()).willReturn(new ResponseEntity<>(HttpStatus.OK));
//		mockMvc.
//				perform(request)
//				.andDo(print())
//				.andExpect(status().isOk());
//	}

//	@Test
//	void captcha_BadRequest() throws Exception {
//		String captchaCode = "captchaCode";
//		var request = get("/api/v1/auth/captcha");
//		when(authController.captcha(captchaCode)).thenReturn(ResponseEntity.badRequest().body("Not Accepted"));
//		verify(authController.captcha(captchaCode));
//		mockMvc
//				.perform(request)
//				.andDo(print())
//				.andExpect(status().isBadRequest());
//	}

	@Test
	void registration_Successful() throws Exception {
		RegistrationDto registrationDto = new RegistrationDto("foo", "bar", "foo@bar.com", "raboof");
		var request = post("/api/v1/auth/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(registrationDto));
		AccountSecureDto accountSecureDto = new AccountSecureDto(
				"foo",
				"bar",
				"foo@bar.com",
				"$2a$10$ZY9xBwLO8HYBoKj9x/lORutRNj5BWUjSWTgvc.xxvGELrZBR2z08C",
				"ROLE_USER");

		when(authController.register(registrationDto)).thenReturn(ResponseEntity.ok().body(accountSecureDto));
		this.mockMvc
				.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(objectMapper.writeValueAsString(accountSecureDto)));
	}

	@Test
	void registration_BadRequest() throws Exception {
		RegistrationDto registrationDto = new RegistrationDto("foo", "bar", "foo@bar.com", "raboof");
		var request = post("/api/v1/auth/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(String.valueOf(registrationDto));

		when(authService.createUser(registrationDto)).thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
		this.mockMvc
				.perform(request)
				.andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	void registration_UnsupportedMediaTypes() throws Exception {
		RegistrationDto registrationDto = new RegistrationDto("foo", "bar", "foo@bar.com", "raboof");
		var request = post("/api/v1/auth/register")
				.content(String.valueOf(registrationDto));

		when(authService.createUser(registrationDto)).thenReturn(new ResponseEntity<>(HttpStatus.OK));
		this.mockMvc
				.perform(request)
				.andDo(print())
				.andExpect(status().isUnsupportedMediaType());
	}

	@Test
	void login_Successful() throws Exception {
		AuthenticateDto authenticateDto = new AuthenticateDto("foo@bar.com", "foobar");
		Duration duration = Duration.ofMillis(31337);
		var request = post("/api/v1/auth/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(authenticateDto));
		Person person = new Person(
				"foo",
				"foobar",
				new ArrayList<SimpleGrantedAuthority>(),
				7L,
				"Artem",
				"Lebedev");
		AccountDto accountDto = new AccountDto(
				"foo",
				"bar",
				"foo@bar.com",
				"$2a$10$ZY9xBwLO8HYBoKj9x/lORutRNj5BWUjSWTgvc.xxvGELrZBR2z08C",
				"ADMIN, USER");

		AuthenticateResponseDto authenticateResponseDto = new AuthenticateResponseDto(
				jwtTokenUtils.generateToken(person, duration),
				jwtTokenUtils.generateToken(person, duration.minusMillis(31000)));

		given(usersClient.getUserDetails("foo@bar.com")).willReturn(accountDto);
		when(authController.login(authenticateDto)).thenReturn(ResponseEntity.ok(authenticateResponseDto));

		mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().json(objectMapper.writeValueAsString(authenticateResponseDto)));
	}

	@Test
	void login_BadRequest() throws Exception {
		AuthenticateDto authenticateDto = new AuthenticateDto("foo@bar.com", "foobar");
		Duration duration = Duration.ofMillis(31337);
		Person person = new Person(
				"foo@bar.com",
				"foobar",
				new ArrayList<SimpleGrantedAuthority>(),
				7L,
				"Artem",
				"Lebedev");
		AccountDto accountDto = new AccountDto(
				"foo",
				"bar",
				"foo@bar.com",
				"foobar",
				"ADMIN, USER");
		AuthenticateResponseDto authenticateResponseDto = new AuthenticateResponseDto(
				jwtTokenUtils.generateToken(person, duration),
				jwtTokenUtils.generateToken(person, duration.minusMillis(31000)));
		when(usersClient.getUserDetails(anyString())).thenReturn(accountDto);
		when(userService.loadUserByUsername(authenticateDto.getEmail())).thenReturn(person);
		doThrow(new RuntimeException()).when(userService).loadUserByUsername(authenticateDto.getEmail());
		verify(userService.loadUserByUsername(String.valueOf(authenticateDto.getEmail().contains(anyString()))));

		mockMvc.perform(post("/api/v1/auth/login")
						.contentType(MediaType.APPLICATION_JSON)
						.content(""))
				.andDo(print())
				.andExpect(status().isBadRequest());

	}

	@Test
	void recoveryPasswordByEmail_Successful() throws Exception {
		PasswordRecoveryDto passwordRecoveryDto = new PasswordRecoveryDto("foo@bar.com");
		var request = post("/api/v1/auth/password/recovery")
				.content(objectMapper.writeValueAsString(passwordRecoveryDto))
				.contentType(MediaType.APPLICATION_JSON);
		when(authController.recoveryPasswordByEmail(passwordRecoveryDto)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

		mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	void recoveryPasswordByEmail_BadRequest() throws Exception {
		PasswordRecoveryDto passwordRecoveryDto = new PasswordRecoveryDto("foo@bar.com");
		var request = post("/api/v1/auth/password/recovery")
				.content("")
				.contentType(MediaType.APPLICATION_JSON);
		when(authController.recoveryPasswordByEmail(passwordRecoveryDto)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

		mockMvc.perform(request)
				.andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	void refresh_Successful() throws Exception {
		Duration duration = Duration.ofMillis(31337);
		Person person = new Person(
				"foo",
				"foobar",
				new ArrayList<SimpleGrantedAuthority>(),
				7L,
				"Artem",
				"Lebedev");
		AuthenticateResponseDto authenticateResponseDto = new AuthenticateResponseDto(
				jwtTokenUtils.generateToken(person, duration),
				jwtTokenUtils.generateToken(person, duration.minusMillis(31000)));
		var request = post("/api/v1/auth/refresh")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(authenticateResponseDto));

		when(authController.refresh(authenticateResponseDto)).thenReturn(new AuthenticateResponseDto(
				jwtTokenUtils.generateToken(person, duration),
				jwtTokenUtils.generateToken(person, duration.minusMillis(31000))));

		mockMvc
				.perform(request)
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().json(objectMapper.writeValueAsString(authenticateResponseDto)))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));

	}

	@Test
	void refresh_BadRequest() throws Exception {
		Duration duration = Duration.ofMillis(31337);
		Person person = new Person(
				"foo",
				"foobar",
				new ArrayList<SimpleGrantedAuthority>(),
				7L,
				"Artem",
				"Lebedev");
		AuthenticateResponseDto authenticateResponseDto = new AuthenticateResponseDto(
				jwtTokenUtils.generateToken(person, duration),
				jwtTokenUtils.generateToken(person, duration.minusMillis(31000)));
		var request = post("/api/v1/auth/refresh")
				.contentType(MediaType.APPLICATION_JSON)
				.content(authenticateResponseDto.toString());

		when(authController.refresh(authenticateResponseDto)).thenReturn(new AuthenticateResponseDto(
				jwtTokenUtils.generateToken(person, duration),
				jwtTokenUtils.generateToken(person, duration.minusMillis(31000))));

		mockMvc
				.perform(request)
				.andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	void setNewPassword_Successful() throws JsonProcessingException {
		String linkId = "exampleLink";
		NewPasswordDto newPasswordDto = new NewPasswordDto("your_newest_password");
		var request = post("/password/exampleLink/recovery/")
				.content(objectMapper.writeValueAsString(newPasswordDto))
				.contentType(MediaType.APPLICATION_JSON);


	}
}
