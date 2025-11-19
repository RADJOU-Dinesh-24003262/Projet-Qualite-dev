package org.example.Exception;

public class InvalidFoodException extends RuntimeException {
    public InvalidFoodException(String message) {
        super(message);
    }
}
