package ru.skillbox.group39.socialnetwork.notifications.dto.notify;

import lombok.Getter;

/**
 * @author Artem Lebedev | 05/09/2023 - 20:42 <p>
 *
POST(0L)<p>
POST_COMMENT(1L)<p>
COMMENT_COMMENT(2L)<p>
FRIEND_REQUEST(3L)<p>
FRIEND_BIRTHDAY(4L)<p>
MESSAGE(5L)<p>
LIKE(6L)<p>
 */

@Getter
public enum ENotificationType {
	POST(0L),
	POST_COMMENT(1L),
	COMMENT_COMMENT(2L),
	FRIEND_REQUEST(3L),
	FRIEND_BIRTHDAY(4L),
	MESSAGE(5L),
	LIKE(6L);

	private final Long num;

	ENotificationType(Long num){
		this.num = num;
	}
}
