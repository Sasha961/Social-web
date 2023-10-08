package ru.skillbox.group39.socialnetwork.notifications.exception.exceptions;

/**
 * @author Artem Lebedev | 08/09/2023 - 21:56
 */
public class CallNotPermittedException extends RuntimeException{
	public CallNotPermittedException(String message) {
		super(message);
	}
}
