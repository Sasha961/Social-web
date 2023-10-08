package ru.skillbox.group39.socialnetwork.authorization.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.group39.socialnetwork.authorization.dto.auth.RegistrationDto;
import ru.skillbox.group39.socialnetwork.authorization.dto.auth.AuthenticateDto;
import ru.skillbox.group39.socialnetwork.authorization.dto.auth.AuthenticateResponseDto;
import ru.skillbox.group39.socialnetwork.authorization.dto.password.NewPasswordDto;
import ru.skillbox.group39.socialnetwork.authorization.dto.password.PasswordRecoveryDto;
import javax.validation.Valid;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.*;


@RestController
@RequestMapping("/api/v1/auth")
//@SecurityRequirement(name = "JWT")
@Tag(name = "Authorization", description = "The Authorization Controller")
public interface AuthController {

	@Operation(description = "Регистрация нового пользователя", tags = {"Authorization service"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation"),
			@ApiResponse(responseCode = "400", description = "Bad request")})
	@RequestMapping(value = "/register",
			consumes = APPLICATION_JSON_VALUE,
			produces = APPLICATION_JSON_VALUE,
			method = RequestMethod.POST)
	Object register(@Valid
	                @Parameter(
			                in = ParameterIn.DEFAULT,
			                description = "id, email, password1, password2, firstname, lastname, captcha code, captcha secret",
			                required = true,
			                schema = @Schema())
	                @RequestBody RegistrationDto request);


	@Operation(summary = "Авторизация", description = "Авторизация на сайте", tags = {"Authorization service"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation", content =
			@Content(
					mediaType = APPLICATION_JSON,
					schema = @Schema(implementation = AuthenticateResponseDto.class))),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "401", description = "Unauthorized")})
	@RequestMapping(
			value = "/login",
			consumes = APPLICATION_JSON_VALUE,
			produces = APPLICATION_JSON_VALUE,
			method = RequestMethod.POST)
	Object login(@Valid
	             @Parameter(
			             in = ParameterIn.DEFAULT,
			             description = "Email, password",
			             required = true,
			             schema = @Schema())
	             @RequestBody AuthenticateDto request);


	@Operation(description = "Получение капчи при регистрации", tags = {"Authorization service"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation"),
			@ApiResponse(responseCode = "400", description = "Bad request")})
	@RequestMapping(value = "/captcha", method = RequestMethod.GET)
	Object captcha();



	@Operation(description = "Заявка на получение письма со ссылкой для восстановления пароля", tags = {"Authorization service"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation"),
			@ApiResponse(responseCode = "400", description = "Bad request")})
	@RequestMapping(
			value = "/password/recovery",
			produces = APPLICATION_JSON_VALUE,
			consumes = APPLICATION_JSON_VALUE,
			method = RequestMethod.POST)
	ResponseEntity<String > recoveryPasswordByEmail(@Valid
	                                                        @Parameter(in = ParameterIn.DEFAULT,
			                                                        description = "Email",
			                                                        required = true,
			                                                        schema = @Schema())
	                                                        @RequestBody PasswordRecoveryDto request);



	@Operation(description = "Обновление токена системы безопасности", tags = {"Authorization service"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation"),
			@ApiResponse(responseCode = "400", description = "Bad request")})
	@RequestMapping(value = "/refresh",
			consumes = APPLICATION_JSON_VALUE,
			method = RequestMethod.POST)
	Object refresh(@Valid
	                          @Parameter(
			                          in = ParameterIn.DEFAULT,
			                          description = "Access token, refresh token",
			                          required = true,
			                          schema = @Schema())
	                          @RequestBody AuthenticateResponseDto request);


	@Operation(description = "Установка нового пароля", tags = {"Authorization service"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation", content =
			@Content(
					mediaType = APPLICATION_JSON,
					schema = @Schema(implementation = RegistrationDto.class))),
			@ApiResponse(responseCode = "400", description = "Bad request")})
	@RequestMapping(value = "/password/{linkId}/recovery/",
			produces = APPLICATION_JSON_VALUE,
			consumes = APPLICATION_JSON_VALUE,
			method = RequestMethod.POST)
	Object setNewPassword(@Valid
	                                               @Parameter(
			                                               in = ParameterIn.PATH,
			                                               required = true,
			                                               schema = @Schema())
	                                               @PathVariable("linkId") String linkId,
	                                               @Parameter(
			                                               in = ParameterIn.DEFAULT,
			                                               required = true,
			                                               schema = @Schema())
	                                               @RequestBody NewPasswordDto request);
}
