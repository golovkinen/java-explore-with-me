package ru.practicum.explore.exceptionhandler;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
