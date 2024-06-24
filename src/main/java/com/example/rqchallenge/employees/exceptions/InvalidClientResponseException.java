package com.example.rqchallenge.employees.exceptions;

public class InvalidClientResponseException extends RuntimeException {

    public InvalidClientResponseException(String message) {
        super(message);
    }
}
