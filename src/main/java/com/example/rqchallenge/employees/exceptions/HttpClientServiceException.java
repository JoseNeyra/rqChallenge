package com.example.rqchallenge.employees.exceptions;

import lombok.Getter;

@Getter
public class HttpClientServiceException extends RuntimeException {

    private int statusCode;

    public HttpClientServiceException(String message) {
        super(message);
    }
}
