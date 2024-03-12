package com.accenture.lambda.exception;

public class BackendResponseException extends RuntimeException {

    public BackendResponseException(final String message) {
        super(message);
    }

    public BackendResponseException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public BackendResponseException(final Throwable cause) {
        super(cause);
    }
}
