package ru.skillbox.group39.socialnetwork.notifications.dto.setting;

import lombok.Getter;

/**
 * @author Artem Lebedev | 24/08/2023 - 19:50 <p>
 * Смотри описание к классу SettingsDto.class
 *
 */

@Getter
public enum ESettingsType {
	LIKE("LIKE"),
	POST("POST"),
	POST_COMMENT("POST_COMMENT"),
	COMMENT_COMMENT("COMMENT_COMMENT"),
	MESSAGE("MESSAGE"),
	FRIEND_REQUEST("FRIEND_REQUEST"),
	FRIEND_BIRTHDAY("FRIEND_BIRTHDAY"),
	SEND_EMAIL_MESSAGE("SEND_EMAIL_MESSAGE");

	private final String type;

	ESettingsType(String type) {
		this.type = type;
	}
}
