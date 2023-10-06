package com.skillbox.socialnetwork.services.Impl;

import com.skillbox.socialnetwork.client.ExternalClient;
import com.skillbox.socialnetwork.dto.RegistrationDto;
import com.skillbox.socialnetwork.dto.auth.AuthenticateResponseDto;
import com.skillbox.socialnetwork.dto.user.CustomUserDetails;
import com.skillbox.socialnetwork.exceptions.AppError;
import com.skillbox.socialnetwork.jwt.JwtTokenUtils;
import com.skillbox.socialnetwork.jwt.dto.JwtRequest;
import com.skillbox.socialnetwork.kafka.KafkaProducer;
import com.skillbox.socialnetwork.services.AuthService;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserServiceImpl userService;
	private final JwtTokenUtils jwtTokenUtils;
	private final AuthenticationManager authenticationManager;
	private final BCryptPasswordEncoder passwordEncoder;
	private final ExternalClient externalClient;


	@Value("${jwt.access-token-duration}")
	private Duration accessTokenDuration;

	@Value("${jwt.refresh-token-duration}")
	private Duration refreshTokenDuration;

	private final SignatureAlgorithm ACCESS_TOKEN_ALG = SignatureAlgorithm.HS512;
	private final SignatureAlgorithm REFRESH_TOKEN_ALG = SignatureAlgorithm.HS512;
	private final KafkaProducer kafkaProducer;

	@Override
	public Object login(@NonNull JwtRequest jwtRequest) {

//		try {
//			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword(), new ArrayList<>()));
//		} catch (BadCredentialsException e){
//			return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Incorrect email or password"), HttpStatus.UNAUTHORIZED);
//		}

		CustomUserDetails userDetails = userService.loadUserByUsername(jwtRequest.getEmail());

		if (userDetails == null) {
			log.error(String.format("User with email '%s' not found", jwtRequest.getEmail()));
			return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), String.format("User with email '%s' not found.", jwtRequest.getEmail())), HttpStatus.UNAUTHORIZED);
		}
		if (!passwordEncoder.matches(jwtRequest.getPassword(), userDetails.getPassword())) {
			log.error(String.format("Wrong password for email '%s'", jwtRequest.getEmail()));
			return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Wrong password!"), HttpStatus.UNAUTHORIZED);
		}

		if (userDetails.isEnabled()) {
			String accessToken = jwtTokenUtils.generateToken(userDetails, accessTokenDuration, ACCESS_TOKEN_ALG);
			String refreshToken = jwtTokenUtils.generateToken(userDetails, refreshTokenDuration, REFRESH_TOKEN_ALG);
			log.info(String.format("Access and Refresh tokens for user with email '%s' generated successfully.", jwtRequest.getEmail()));
			AuthenticateResponseDto authenticateResponseDto = new AuthenticateResponseDto(accessToken, refreshToken);
//			kafkaProducer.sendJWTToken(authenticateResponseDto);
			return ResponseEntity.ok(authenticateResponseDto);
		} else {
			log.error(String.format("Incorrect email '%s' or password", jwtRequest.getEmail()));
			return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Incorrect email or password"), HttpStatus.UNAUTHORIZED);
		}
	}

	@Override
	public Object refreshTokens(JwtRequest jwtRequest) {

		/*
		- получить пэйлоад, извлеч юзернейм
		- сравнить в обоих токенах пэйлоад
		- проверить срок истечения
		- Если истек только достпа - то генерим два
		- если оба истекли, перенапрявляем на логин
		 */
		return null;
	}

	@Override
	public void createUser(@NotNull RegistrationDto registrationDto) {
		log.info(String.format(
				"Sending registration info: '%s', '%s', '%s', '%s' to user-micro-service",
				registrationDto.getFirstName(),
				registrationDto.getLastName(),
				registrationDto.getEmail(),
				passwordEncoder.encode(registrationDto.getPassword())));
		kafkaProducer.sendMessage(registrationDto.toString());
		externalClient.createUser(
				new RegistrationDto(
						registrationDto.getFirstName(),
						registrationDto.getLastName(),
						registrationDto.getEmail(),
						passwordEncoder.encode(registrationDto.getPassword())));
	}
}
