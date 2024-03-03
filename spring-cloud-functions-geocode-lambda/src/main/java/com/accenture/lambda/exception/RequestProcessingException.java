package com.accenture.lambda.exception;

public class RequestProcessingException extends RuntimeException {

    public RequestProcessingException(final String message) {
        super(message);
    }
}
