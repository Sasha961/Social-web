package ru.skillbox.group39.socialnetwork.notifications.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.bind.annotation.*;
import ru.skillbox.group39.socialnetwork.notifications.dto.count.NotificationsCountDto;
import ru.skillbox.group39.socialnetwork.notifications.dto.event.EventNotificationDto;
import ru.skillbox.group39.socialnetwork.notifications.dto.notify.*;
import ru.skillbox.group39.socialnetwork.notifications.dto.setting.SettingsDto;
import ru.skillbox.group39.socialnetwork.notifications.dto.setting.SettingChangeDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequestMapping("/api/v1/notifications")
public interface NotificationController {

	@Operation(
			description = "Получить счетчик количества событий",
			security = {@SecurityRequirement(name = "JWT")}, tags = {"NotificationSimpleModel service"})
	@ApiResponses(
			value = {@ApiResponse(responseCode = "200", description = "Successful operation",
					content = @Content(schema = @Schema(implementation = NotificationsCountDto.class))),
					@ApiResponse(responseCode = "400", description = "Bad request"),
					@ApiResponse(responseCode = "401", description = "Unauthorized")})
	@RequestMapping(
			value = "/count",
			method = RequestMethod.GET)
	Object getCount();

	// ---------------------------------------------------------------------------------------------------------------------

	@Operation(
			description = "Получение страницы событий",
			security = {@SecurityRequirement(name = "JWT")}, tags = {"NotificationSimpleModel service"})
	@ApiResponses(
			value = {
					@ApiResponse(
							responseCode = "200",
							description = "Successful operation",
							content = @Content(mediaType = "*/*", schema = @Schema(implementation = NotificationStampedDto.class))),
					@ApiResponse(responseCode = "400", description = "Bad request"),
					@ApiResponse(responseCode = "401", description = "Unauthorized")})
	@RequestMapping(value = "/",
			produces = APPLICATION_JSON_VALUE,
			method = RequestMethod.GET)
	Object getPageNotifications(
			@RequestParam(required = false, defaultValue = "0") Integer page,
			@RequestParam(required = false, defaultValue = "5") Integer size,
			@RequestParam(required = false, defaultValue = "timestamp") @Nullable String sort);

	// ---------------------------------------------------------------------------------------------------------------------
	@Operation(
			description = "Создание события",
			security = {@SecurityRequirement(name = "JWT")},
			tags = {"NotificationSimpleModel service"})
	@ApiResponses(
			value = {
					@ApiResponse(responseCode = "200", description = "Successful operation"),
					@ApiResponse(responseCode = "400", description = "Bad request"),
					@ApiResponse(responseCode = "401", description = "Unauthorized")})
	@RequestMapping(
			value = "/add",
			consumes = APPLICATION_JSON_VALUE,
			method = RequestMethod.POST)
	Object addEvent(@Parameter(in = ParameterIn.DEFAULT, required = true)
	                @Valid @RequestBody EventNotificationDto eventNotificationDto);
// ---------------------------------------------------------------------------------------------------------------------

	@Operation(
			description = "Отметить все события, как прочитанные",
			security = {@SecurityRequirement(name = "JWT")},
			tags = {"NotificationSimpleModel service"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "401", description = "Unauthorized")})
	@RequestMapping(value = "/read",
			method = RequestMethod.PUT)
	Object setAllRead(HttpServletRequest request);

	// ---------------------------------------------------------------------------------------------------------------------

	@Operation(
			description = "Получение настроек оповещений",
			security = {@SecurityRequirement(name = "JWT")},
			tags = {"NotificationSimpleModel service"})
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Successful operation",
					content = @Content(mediaType = "*/*", schema = @Schema(implementation = SettingsDto.class))),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "401", description = "Unauthorized")})
	@RequestMapping(
			value = "/settings",
			produces = APPLICATION_JSON_VALUE,
			method = RequestMethod.GET)
	Object getSetting();

// ---------------------------------------------------------------------------------------------------------------------

	@Operation(
			description = "Создание настроек оповещений",
			security = {@SecurityRequirement(name = "JWT")},
			tags = {"NotificationSimpleModel service"})
	@ApiResponses(
			value = {@ApiResponse(
					responseCode = "200",
					description = "Successful operation",
					content = @Content(mediaType = "*/*", schema = @Schema(implementation = Boolean.class))),
					@ApiResponse(responseCode = "400", description = "Bad request"),
					@ApiResponse(responseCode = "401", description = "Unauthorized")})
	@RequestMapping(value = "/settings",
			produces = APPLICATION_JSON_VALUE,
			method = RequestMethod.POST)
	Object createSetting(@Parameter(in = ParameterIn.QUERY, required = false)
	                     @RequestParam(required = false) Long userId);

// ---------------------------------------------------------------------------------------------------------------------

	@Operation(
			description = "Коррекция настроек оповещений",
			security = {@SecurityRequirement(name = "JWT")},
			tags = {"NotificationSimpleModel service"})
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successful operation"),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "401", description = "Unauthorized")})
	@RequestMapping(
			value = "/settings",
			consumes = APPLICATION_JSON_VALUE,
			method = RequestMethod.PUT)
	Object changeSetting(
			@Parameter(in = ParameterIn.DEFAULT, required = true)
			@Valid @RequestBody SettingChangeDto body);
}
