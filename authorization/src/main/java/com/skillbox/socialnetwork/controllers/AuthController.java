package com.skillbox.socialnetwork.controllers;

import com.skillbox.socialnetwork.dto.auth.AuthenticateResponseDto;
import com.skillbox.socialnetwork.dto.kafka.JsonMessage;
import com.skillbox.socialnetwork.dto.password.NewPasswordDto;
import com.skillbox.socialnetwork.dto.password.PasswordRecoveryDto;
import com.skillbox.socialnetwork.dto.RegistrationDto;
import com.skillbox.socialnetwork.jwt.dto.JwtRequest;
import com.skillbox.socialnetwork.jwt.dto.JwtResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//@FeignClient(value = "Authorization micro service", url = "http://localhost:8084/api/v1/auth")
public interface AuthController {

	@Operation(summary = "Авторизация", description = "Авторизация на сайте", tags = {"Authorization service"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation", content =
			@Content(
					mediaType = "application/json",
					schema = @Schema(implementation = JwtResponse.class))),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "401", description = "Unauthorized")})
	@RequestMapping(
			value = "/login",
			consumes = {"application/json"},
			method = RequestMethod.POST)
	Object login(@Valid
	             @Parameter(
			             in = ParameterIn.DEFAULT,
			             description = "Email, password",
			             required = true,
			             schema = @Schema())
	             @RequestBody JwtRequest jwtRequest);


	@Operation(summary = "", description = "Получение капчи при регистрации", tags = {"Authorization service"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation"),
			@ApiResponse(responseCode = "400", description = "Bad request")})
	@RequestMapping(value = "/captcha", method = RequestMethod.GET)
	ResponseEntity<Void> captcha();


	@Operation(summary = "", description = "Заявка на получение письма со ссылкой для восстановления пароля", tags = {"Authorization service"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation"),
			@ApiResponse(responseCode = "400", description = "Bad request")})
	@RequestMapping(
			value = "/password/recovery",
			produces = {"application/json"},
			consumes = {"application/json"},
			method = RequestMethod.POST)
	ResponseEntity<RegistrationDto> recoveryPasswordByEmail(@Valid
	                                                        @Parameter(in = ParameterIn.DEFAULT,
			                                                        description = "Email",
			                                                        required = true,
			                                                        schema = @Schema())
	                                                        @RequestBody PasswordRecoveryDto body);


	@Operation(summary = "", description = "Обновление токена системы безопасности", tags = {"Authorization service"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation"),
			@ApiResponse(responseCode = "400", description = "Bad request")})
	@RequestMapping(value = "/refresh",
			consumes = {"application/json"},
			method = RequestMethod.POST)
	ResponseEntity<?> refresh(@Valid
	                          @Parameter(
			                          in = ParameterIn.DEFAULT,
			                          description = "Access token, refresh token",
			                          required = true,
			                          schema = @Schema())
	                          @RequestBody AuthenticateResponseDto body);


	@Operation(summary = "", description = "Регистрация нового пользователя", tags = {"Authorization service"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation"),
			@ApiResponse(responseCode = "400", description = "Bad request")})
	@RequestMapping(value = "/register",
			consumes = {"application/json"},
			method = RequestMethod.POST)
	void register(@Valid
	                           @Parameter(
			                           in = ParameterIn.DEFAULT,
			                           description = "id, email, password1, password2, firstname, lastname, captcha code, captcha secret",
			                           required = true,
			                           schema = @Schema())
	                           @RequestBody RegistrationDto body);


	@Operation(summary = "", description = "Установка нового пароля", tags = {"Authorization service"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation", content =
			@Content(
					mediaType = "application/json",
					schema = @Schema(implementation = RegistrationDto.class))),
			@ApiResponse(responseCode = "400", description = "Bad request")})
	@RequestMapping(value = "/password/recovery/{linkId}",
			produces = {"application/json"},
			consumes = {"application/json"},
			method = RequestMethod.POST)
	ResponseEntity<RegistrationDto> setNewPassword(@Valid
	                                               @Parameter(
			                                               in = ParameterIn.PATH,
			                                               description = "",
			                                               required = true,
			                                               schema = @Schema())
	                                               @PathVariable("linkId") String linkId,
	                                               @Parameter(
			                                               in = ParameterIn.DEFAULT,
			                                               description = "",
			                                               required = true,
			                                               schema = @Schema())
	                                               @RequestBody NewPasswordDto body);


	@RequestMapping(
			value = "/getClaims",
			method = RequestMethod.POST)
	Object getClaims(@RequestHeader("Authorization") String bearerToken);

	@RequestMapping(
			value = "/getUserNameFromToken",
			method = RequestMethod.POST)
	Object getUserNameFromToken(@RequestHeader("Authorization") String bearerToken);

	@RequestMapping(value = "/sendMessages", method = RequestMethod.POST)
	public void sendMessages();

	@RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
	public void sendMessage(@RequestParam String message);

//	@RequestMapping(value = "/sendJsonMessage", method = RequestMethod.POST)
//	public void sendMessage(@RequestBody JsonMessage jsonMessage);
}
