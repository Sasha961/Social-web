package com.example.dto.notify;

import lombok.Getter;

/**
 * @author Artem Lebedev | 05/09/2023 - 20:42 <p>
 *
 * 	POST<p>
 * 	POST_COMMENT<p>
 * 	COMMENT_COMMENT<p>
 * 	FRIEND_REQUEST<p>
 * 	FRIEND_BIRTHDAY<p>
 * 	MESSAGE<p>
 * 	LIKE<p>
 */

@Getter
public enum ENotificationType {
	POST(1L),
	POST_COMMENT(2L),
	COMMENT_COMMENT(3L),
	FRIEND_REQUEST(4L),
	FRIEND_BIRTHDAY(5L),
	MESSAGE(6L),
	LIKE(7L);

	private final Long id;

	ENotificationType(Long id){
		this.id = id;
	}
}
