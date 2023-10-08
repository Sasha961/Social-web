package ru.skillbox.group39.socialnetwork.notifications.exception.exceptions;

/**
 * @author Artem Lebedev | 29/08/2023 - 21:52
 */

public class RequestRejectedException extends RuntimeException{
	public RequestRejectedException(String message) {
		super(message);
	}
}
