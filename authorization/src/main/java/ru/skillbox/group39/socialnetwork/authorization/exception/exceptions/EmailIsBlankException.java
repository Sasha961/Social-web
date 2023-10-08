package ru.skillbox.group39.socialnetwork.authorization.exception.exceptions;

public class EmailIsBlankException extends RuntimeException{
    public EmailIsBlankException(String message) {
        super(message);
    }
}
