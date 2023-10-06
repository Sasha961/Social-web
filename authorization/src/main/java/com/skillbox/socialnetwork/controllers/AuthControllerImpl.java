package com.skillbox.socialnetwork.controllers;

import com.skillbox.socialnetwork.dto.RegistrationDto;
import com.skillbox.socialnetwork.dto.auth.AuthenticateResponseDto;
import com.skillbox.socialnetwork.dto.kafka.JsonMessage;
import com.skillbox.socialnetwork.dto.password.NewPasswordDto;
import com.skillbox.socialnetwork.dto.password.PasswordRecoveryDto;
import com.skillbox.socialnetwork.exceptions.AppError;
import com.skillbox.socialnetwork.jwt.JwtTokenUtils;
import com.skillbox.socialnetwork.jwt.dto.JwtRequest;
import com.skillbox.socialnetwork.kafka.KafkaProducer;
import com.skillbox.socialnetwork.responses.AuthResponses;
import com.skillbox.socialnetwork.services.AuthService;
import com.skillbox.socialnetwork.services.Impl.UserServiceImpl;
import com.skillbox.socialnetwork.services.PasswordRecoveryService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@Tag(name = "Authorization", description = "The Authorization Controller")
@RestController
@Getter
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

	private final PasswordRecoveryService passwordRecoveryService;
	private final AuthService authService;
	private final AuthResponses authResponses;
	private final UserServiceImpl userService;
	private final JwtTokenUtils jwtTokenUtils;
	private final AuthenticationManager authenticationManager;
	private final KafkaProducer kafkaProducer;


	@Override
	public Object login(@NonNull JwtRequest jwtRequest) {
		return authService.login(jwtRequest);
	}

	@Override
	public void register(@Valid
	                     @Parameter(
			                     in = ParameterIn.DEFAULT,
			                     description = "Email, password, confirm password, firstname, lastname, captcha",
			                     required = true,
			                     schema = @Schema())
	                     @RequestBody RegistrationDto registrationDto) {
		authService.createUser(registrationDto);
	}


	@Override
	public ResponseEntity<RegistrationDto> recoveryPasswordByEmail(
			@Parameter(
					in = ParameterIn.DEFAULT,
					description = "Email",
					required = true,
					schema = @Schema())
			@Valid
			@RequestBody @NonNull PasswordRecoveryDto body) {
		if (!body.getEmail().equals("")) {
			return new ResponseEntity<>(passwordRecoveryService.getRequestForEmail(body.getEmail()), HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
	}

	@Override
	public ResponseEntity<Void> captcha() {
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> refresh(@Valid
	                                 @Parameter(
			                                 in = ParameterIn.DEFAULT,
			                                 description = "Access token, refresh token",
			                                 required = true,
			                                 schema = @Schema())
	                                 @RequestBody AuthenticateResponseDto body) {
		return new ResponseEntity<>(authResponses.getRefreshResponseOK(), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('USER')")
	@Override
	public ResponseEntity<RegistrationDto> setNewPassword(@Valid
	                                                      @Parameter(
			                                                      in = ParameterIn.PATH,
			                                                      description = "New password",
			                                                      required = true,
			                                                      schema = @Schema())
	                                                      @PathVariable("linkId") String linkId,
	                                                      @Parameter(
			                                                      in = ParameterIn.DEFAULT,
			                                                      description = "New password",
			                                                      required = true,
			                                                      schema = @Schema())
	                                                      @RequestBody @NonNull NewPasswordDto body) {

		return new ResponseEntity<>(passwordRecoveryService.getRequestForEmail(body.getPassword()), HttpStatus.OK);
	}


	//----------------------------------------------------------------------------------------------------------------------
	@Override
	public Object getClaims(@RequestHeader("Authorization") @NonNull String bearerToken) {
		final String[] parts = bearerToken.split(" ");
		final String jwtToken = parts[1];
		final Boolean result = jwtTokenUtils.isJwtTokenIsNotExpired(jwtToken);
		if (result) {
			return jwtTokenUtils.getAllClaimsFromToken(jwtToken);

		}
		return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), ""), HttpStatus.UNAUTHORIZED);
	}

	@Override
	public Object getUserNameFromToken(@NonNull String bearerToken) {
		return jwtTokenUtils.getUsername(bearerToken.substring(7));
	}

	@Override
	public void sendMessages() {
		kafkaProducer.sendMessages();
	}

	@Override
	public void sendMessage(String message) {
		kafkaProducer.sendMessage(message);
	}

//	@Override
//	public void sendMessage(JsonMessage jsonMessage) {
//		kafkaProducer.sendJsonMessage(jsonMessage);
//	}
}
