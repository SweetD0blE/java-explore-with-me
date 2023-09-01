package ru.practicum.ewm.main_service.exception;

public class ValidationException extends RuntimeException {
    public ValidationException(final String message) {
        super(message);
    }
}