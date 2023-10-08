package ru.skillbox.group39.socialnetwork.notifications.controller.Impl;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.group39.socialnetwork.notifications.dto.event.EventNotificationDto;
import ru.skillbox.group39.socialnetwork.notifications.dto.setting.SettingChangeDto;
import ru.skillbox.group39.socialnetwork.notifications.exception.ErrorResponse;
import ru.skillbox.group39.socialnetwork.notifications.service.NotificationService;
import ru.skillbox.group39.socialnetwork.notifications.controller.NotificationController;
import ru.skillbox.group39.socialnetwork.notifications.service.SettingsService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import static ru.skillbox.group39.socialnetwork.notifications.security.service.UserDetailsServiceImpl.getPrincipalId;

@RestController
@SecurityRequirement(name = "JWT")
@Tag(name = "Notifications service", description = "Сервис уведомлений")
@Slf4j
@RequiredArgsConstructor
public class NotificationControllerImpl implements NotificationController {
	private final NotificationService notificationService;
	private final SettingsService settingsService;

	@Override
	public Object getCount() {
		log.info(" * GET /count");
		return notificationService.getCount();
	}

	@Override
	public Object getPageNotifications(Integer page, Integer size, String sort) {
		log.info(" * GET \"/\"");
		log.info(" * Payload: page?{}, size?{}, sort?{}", page, size, sort);
		Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
		log.info(" * Pageable: {}", pageable);
		return notificationService.getPageStampedNotifications(pageable);
	}

	@Override
	public Object addEvent(@Parameter(in = ParameterIn.DEFAULT, required = true)
	                       @Valid @RequestBody EventNotificationDto eventNotificationDto) {
		log.info(" * POST \"/addEvent\"");
		log.info(" * Payload: {}", eventNotificationDto);
		return notificationService.addEvent(eventNotificationDto);
	}

	@Override
	public Object setAllRead(@NotNull HttpServletRequest request) {
		log.info(" * PUT \"/read\"");
		if (!(request.getParameterMap().isEmpty()) | (request.getHeader("Content-Type") != null)) {
			return new ResponseEntity<>(new ErrorResponse("Wrong args/method/headers/etc", HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
		}
		return notificationService.setAllRead();
	}

	@Override
	public Object getSetting() {
		return settingsService.getSettings();
	}

	@Override
	public Object createSetting(
			@Parameter(
					in = ParameterIn.QUERY,
					description = "id пользователя в базе данных",
					schema = @Schema())
			@RequestParam(required = false) Long userId) {
		log.info(" * POST /settings");
		log.info(" * Payload: {}", userId);
		if (userId == null) {
			return settingsService.createSettings(getPrincipalId());
		}
		return settingsService.createSettings(userId);
	}

	@Override
	public Object changeSetting(
			@Parameter(
					in = ParameterIn.DEFAULT,
					description = "JSON с двумя полями: ESettingType, Boolean",
					required = true,
					schema = @Schema())
			@Valid @RequestBody SettingChangeDto settingChangeDto) {
		log.info(" * PUT /settings");
		log.info(" * Payload: {}", settingChangeDto);

		return settingsService.changeSetting(settingChangeDto);
	}
}

