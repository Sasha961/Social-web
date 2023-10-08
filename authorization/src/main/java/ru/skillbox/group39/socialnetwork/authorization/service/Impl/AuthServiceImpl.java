package ru.skillbox.group39.socialnetwork.authorization.service.Impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skillbox.group39.socialnetwork.authorization.kafka.KafkaProducer;
import ru.skillbox.group39.socialnetwork.authorization.service.AuthService;
import ru.skillbox.group39.socialnetwork.authorization.client.UsersClient;
import ru.skillbox.group39.socialnetwork.authorization.client.HealthChecker;
import ru.skillbox.group39.socialnetwork.authorization.dto.auth.RegistrationDto;
import ru.skillbox.group39.socialnetwork.authorization.dto.auth.AuthenticateDto;
import ru.skillbox.group39.socialnetwork.authorization.dto.auth.AuthenticateResponseDto;
import ru.skillbox.group39.socialnetwork.authorization.dto.password.NewPasswordDto;
import ru.skillbox.group39.socialnetwork.authorization.dto.user.AccountSecureDto;
import ru.skillbox.group39.socialnetwork.authorization.exception.ErrorResponse;
import ru.skillbox.group39.socialnetwork.authorization.security.jwt.JwtTokenUtils;
import ru.skillbox.group39.socialnetwork.authorization.security.model.Person;

import java.time.Duration;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserServiceImpl userService;
	private final JwtTokenUtils jwtTokenUtils;
	private final AuthenticationManager authenticationManager;
	private final BCryptPasswordEncoder passwordEncoder;
	private final UsersClient usersClient;
	private final ObjectMapper objectMapper;

	@Value("${users-service.host}")
	private String USERS_HOST;
	@Value("${users-service.port}")
	private String USERS_PORT;
	@Value("${jwt.access-token-duration}")
	private Duration ACCESS_TOKEN_DURATION;
	@Value("${jwt.refresh-token-duration}")
	private Duration REFRESH_TOKEN_DURATION;

	private final KafkaProducer kafkaProducer;

	@Override
	public ResponseEntity<?> login(@NonNull AuthenticateDto inParam) {

//		try {
//			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword(), new ArrayList<>()));
//		} catch (BadCredentialsException e){
//			return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Incorrect email or password"), HttpStatus.UNAUTHORIZED);
//		}

		Person person = userService.loadUserByUsername(inParam.getEmail());

		ResponseEntity<ErrorResponse> UNAUTHORIZED = checkingPswrd(inParam, person);
		if (UNAUTHORIZED != null) {
			return UNAUTHORIZED;
		}

		if (person.isEnabled()) {
			String accessToken = jwtTokenUtils.generateToken(person, ACCESS_TOKEN_DURATION);
			String refreshToken = jwtTokenUtils.generateToken(person, REFRESH_TOKEN_DURATION);
			log.info(String.format(" * Access and Refresh tokens for user with email '%s' generated successfully.", inParam.getEmail()));
			AuthenticateResponseDto outParam = new AuthenticateResponseDto(accessToken, refreshToken);

			kafkaProducer.sendMessage("notify-topic", String.format("User with email '%s' logged in", inParam.getEmail()));

			return ResponseEntity.ok(outParam);
		} else {
			log.error(String.format(" ! Incorrect email '%s' or password", inParam.getEmail()));
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, "Incorrect email or password"), HttpStatus.BAD_REQUEST);
		}
	}

	@Nullable
	private ResponseEntity<ErrorResponse> checkingPswrd(@NotNull AuthenticateDto inParam, @NotNull Person person) {
		if (inParam.getPassword() == null | Objects.equals(inParam.getPassword(), "")) {
			log.error(String.format(" ! Password for email '%s' is empty", inParam.getEmail()));
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, "Password can't be empty"), HttpStatus.BAD_REQUEST);
		} else if (!passwordEncoder.matches(inParam.getPassword(), person.getPassword())) {
			log.error(String.format(" ! Wrong password for email '%s'", inParam.getEmail()));
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.UNAUTHORIZED, "Wrong password"), HttpStatus.UNAUTHORIZED);
		}
		return null;
	}

	@Override
	public ResponseEntity<?> refreshTokens(@NotNull AuthenticateResponseDto inParam) {
		String oldAccessToken = inParam.getAccessToken();
		String oldRefreshToken = inParam.getRefreshToken();

		if (!jwtTokenUtils.isJwtTokenIsNotExpired(oldRefreshToken)) {
			return new ResponseEntity<>(
					new ErrorResponse(HttpStatus.BAD_REQUEST, "Refresh token is expired"), HttpStatus.BAD_REQUEST);
		}

		Claims oldAccessTokenClaims = jwtTokenUtils.getAllClaimsFromToken(oldAccessToken);
		Claims oldRefreshTokenClaims = jwtTokenUtils.getAllClaimsFromToken(oldRefreshToken);
		log.info(" * Claims from accessToken : {}, claims from refreshToken: {}", oldAccessTokenClaims, oldRefreshTokenClaims);

		ResponseEntity<ErrorResponse> BAD_REQUEST = checkingClaimsFromTokens(oldAccessTokenClaims, oldRefreshTokenClaims);
		if (BAD_REQUEST != null) {
			return BAD_REQUEST;
		}

		Person person = userService.loadUserByUsername(oldAccessTokenClaims.getSubject());
		AuthenticateResponseDto outParam = new AuthenticateResponseDto();

		if (person != null) {
			String newAccessToken = jwtTokenUtils.generateToken(person, ACCESS_TOKEN_DURATION);
			outParam.setAccessToken(newAccessToken);
			outParam.setRefreshToken(oldRefreshToken);
		}
		return ResponseEntity.ok(outParam);
	}

	@Nullable
	private static ResponseEntity<ErrorResponse> checkingClaimsFromTokens(@NotNull Claims oldAccessTokenClaims, @NotNull Claims oldRefreshTokenClaims) {
		if (!oldAccessTokenClaims.getSubject().equals(oldRefreshTokenClaims.getSubject())
				| !oldAccessTokenClaims.get("firstName").equals(oldRefreshTokenClaims.get("firstName"))
				| !oldAccessTokenClaims.get("lastName").equals(oldRefreshTokenClaims.get("lastName"))
				| !oldAccessTokenClaims.get("userId").equals(oldRefreshTokenClaims.get("userId"))) {
			return new ResponseEntity<>(
					new ErrorResponse(HttpStatus.BAD_REQUEST, "Claims from tokens mismatch"), HttpStatus.BAD_REQUEST);
		}
		return null;
	}

	@Override
	public ResponseEntity<?> doCaptcha(@NotNull String captchaCode) {
		String captchaSecret = "captchaSecret";
		if (captchaCode.equals(captchaSecret)) {
			return new ResponseEntity<>("Accepted", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Not Accepted", HttpStatus.BAD_REQUEST);
		}
	}

	@Override
	public ResponseEntity<?> setNewPassword(String linkId, @NotNull NewPasswordDto inParam) {
		Person userDetails = userService.loadUserByUsername(linkId);
		RegistrationDto response = new RegistrationDto(userDetails.getFirstName(), userDetails.getLastName(), userDetails.getUsername(), inParam.getPassword());
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> createUser(@NotNull RegistrationDto inParam) {
		log.info(" * authService-createUser with " + inParam);
		produceKafkaMessage(inParam);

		if (isAnyFieldOfRegistrationDtoIsEmpty(inParam)) {
			log.error(" * All fields must be filled in");
			return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST, "All fields must be filled in"), HttpStatus.BAD_REQUEST);
		}

		String passwordBCrypt = passwordEncoder.encode(inParam.getPassword());
		log.info(String.format(
				" * authService-createUser sent registration info: '%s', '%s', '%s', '%s' to user-micro-service",
				inParam.getFirstName(), inParam.getLastName(),
				inParam.getEmail(), passwordBCrypt));

		HealthChecker.checkHealthyService(USERS_HOST, Integer.valueOf(USERS_PORT));

		AccountSecureDto accountSecureDto = usersClient.createUser(
				new RegistrationDto(inParam.getFirstName(), inParam.getLastName(),
						inParam.getEmail(), passwordBCrypt));

		kafkaProducer.sendMessage("notify-topic", String.format("User with email '%s' registered", inParam.getEmail()));
		return new ResponseEntity<>(accountSecureDto, HttpStatus.OK);
	}

	private @NotNull Boolean isAnyFieldOfRegistrationDtoIsEmpty(@NotNull RegistrationDto inParam) {
		return (inParam.getEmail() == null | Objects.requireNonNull(inParam.getEmail()).isEmpty()) |
				(inParam.getPassword() == null | Objects.requireNonNull(inParam.getPassword()).isEmpty()) |
				inParam.getFirstName() == null |
				inParam.getLastName() == null;
	}

	private void produceKafkaMessage(@NotNull RegistrationDto registrationDto) {
		String registrationAsString;
		try {
			registrationAsString = objectMapper.writeValueAsString(registrationDto);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
		log.info(" * Registration data produced and transfer to kafkaProducer");
		kafkaProducer.sendMessage("auth-topic", registrationAsString);
	}

}
