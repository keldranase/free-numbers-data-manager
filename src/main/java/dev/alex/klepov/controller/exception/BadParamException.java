package dev.alex.klepov.controller.exception;

public class BadParamException extends RuntimeException {
    public BadParamException(String message) {
        super(message);
    }
}
