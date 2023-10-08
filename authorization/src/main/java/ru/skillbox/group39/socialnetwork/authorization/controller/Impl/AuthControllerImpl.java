package ru.skillbox.group39.socialnetwork.authorization.controller.Impl;

import cn.apiclub.captcha.Captcha;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.group39.socialnetwork.authorization.captcha.CaptchaUtils;
import ru.skillbox.group39.socialnetwork.authorization.controller.AuthController;
import ru.skillbox.group39.socialnetwork.authorization.kafka.KafkaProducer;
import ru.skillbox.group39.socialnetwork.authorization.dto.auth.RegistrationDto;
import ru.skillbox.group39.socialnetwork.authorization.dto.auth.AuthenticateDto;
import ru.skillbox.group39.socialnetwork.authorization.dto.auth.AuthenticateResponseDto;
import ru.skillbox.group39.socialnetwork.authorization.dto.password.NewPasswordDto;
import ru.skillbox.group39.socialnetwork.authorization.dto.password.PasswordRecoveryDto;
import ru.skillbox.group39.socialnetwork.authorization.security.jwt.JwtTokenUtils;
import ru.skillbox.group39.socialnetwork.authorization.service.AuthService;
import ru.skillbox.group39.socialnetwork.authorization.service.UserService;

import javax.validation.Valid;
import java.util.Objects;

@Slf4j
@Tag(name = "Authorization", description = "The Authorization Controller")
@RestController
@Getter
@RequiredArgsConstructor
public class AuthControllerImpl implements AuthController {

	private String captchaSecret;

	private final AuthService authService;
	private final UserService userService;
	private final JwtTokenUtils jwtTokenUtils;
	private final AuthenticationManager authenticationManager;
	private final KafkaProducer kafkaProducer;


	@Override
	public Object register(@Valid
	                       @Parameter(
			                       in = ParameterIn.DEFAULT,
			                       description = "Email, password, confirm password, firstname, lastname, captcha",
			                       required = true,
			                       schema = @Schema())
	                       @RequestBody @NotNull RegistrationDto request) {
		log.info(" * POST /register with email '{}' and password '{}'", request.getEmail(), request.getPassword());
		log.info(request.toString());
		log.info(" * Captcha user's answer - '{}'", request.getCaptchaCode());

		if (!Objects.equals(request.getCaptchaCode(), captchaSecret)) {
			return new ResponseEntity<>("Wrong captcha answer", HttpStatus.BAD_REQUEST);
		} else {
			request.setCaptchaSecret(captchaSecret);
		}
		return authService.createUser(request);
	}

	@Override
	public Object login(@NonNull AuthenticateDto request) {
		log.info(" * POST /login with email '{}' and password '{}'", request.getEmail(), request.getPassword());
		return authService.login(request);
	}

	@Override
	public Object refresh(@Valid
	                      @Parameter(
			                      in = ParameterIn.DEFAULT,
			                      description = "Access token, refresh token",
			                      required = true,
			                      schema = @Schema())
	                      @RequestBody @NotNull AuthenticateResponseDto request) {
		log.info(" * POST /refresh with access token '{}' and refresh token '{}'", request.getAccessToken(), request.getRefreshToken());
		return authService.refreshTokens(request);
	}


	@Override
	public ResponseEntity<String> recoveryPasswordByEmail(
			@Parameter(
					in = ParameterIn.DEFAULT,
					description = "Email",
					required = true,
					schema = @Schema())
			@Valid
			@RequestBody @NonNull PasswordRecoveryDto request) {
		log.info(" * POST /password/recovery with email '{}' ", request.getEmail());
		return new ResponseEntity<>("Your request in progress", HttpStatus.OK);
	}

	@Override
	public Object captcha() {
		log.info(" * GET /captcha");
		Captcha captcha = CaptchaUtils.createCaptcha(240, 70);
		this.captchaSecret = captcha.getAnswer();
		log.info(" * Captcha code (expected answer) - '{}'", captchaSecret);
		return CaptchaUtils.encodeCaptcha(captcha);
	}

	@Override
	public Object setNewPassword(@Valid

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
	                             @RequestBody @NonNull NewPasswordDto request) {
		log.info(" * POST /password/{linkId}/recovery/ with new password '{}'", request.getPassword());
		return authService.setNewPassword(linkId, request);
	}
}
