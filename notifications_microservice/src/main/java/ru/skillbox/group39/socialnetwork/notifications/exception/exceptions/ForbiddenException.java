package ru.skillbox.group39.socialnetwork.notifications.exception.exceptions;

/**
 * @author Artem Lebedev | 29/08/2023 - 23:44
 */

public class ForbiddenException extends RuntimeException{
	public ForbiddenException(String message) {
		super(message);
	}
}
