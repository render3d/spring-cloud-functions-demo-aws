package com.accenture.lambda.exception;

public class BackendResponseException extends RuntimeException {

    public BackendResponseException(final String message) {
        super(message);
    }
}
