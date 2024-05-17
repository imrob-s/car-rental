package dev.imrob.carrental.exceptions;

public class LoginUniqueViolationException extends RuntimeException {
    public LoginUniqueViolationException(String message) {
        super(message);
    }
}
