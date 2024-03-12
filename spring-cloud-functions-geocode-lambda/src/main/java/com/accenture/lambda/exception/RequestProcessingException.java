package com.accenture.lambda.exception;

public class RequestProcessingException extends RuntimeException {

    public RequestProcessingException(final String message) {
        super(message);
    }

    public RequestProcessingException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public RequestProcessingException(final Throwable cause) {
        super(cause);
    }
}
