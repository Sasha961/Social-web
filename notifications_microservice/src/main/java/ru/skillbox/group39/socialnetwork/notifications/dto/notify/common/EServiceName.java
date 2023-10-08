package ru.skillbox.group39.socialnetwork.notifications.dto.notify.common;

import lombok.Getter;

/**
 * @author Artem Lebedev | 05/09/2023 - 12:04 <p>
 * 	MESSAGES(0L)<p>
 * 	ADMIN(1L)<p>
 * 	FRIENDS(2L)<p>
 * 	AUTH(3L)<p>
 * 	USERS(4L)<p>
 * 	NOTIFY(5L)<p>
 * 	POSTS(6L)<p>
 */

@Getter
public enum EServiceName {
	MESSAGES(0L),
	ADMIN(1L),
	FRIENDS(2L),
	AUTH(3L),
	USERS(4L),
	NOTIFY(5L),
	POSTS(6L);

	private final Long num;

	EServiceName(Long num){
		this.num = num;
	}
}
