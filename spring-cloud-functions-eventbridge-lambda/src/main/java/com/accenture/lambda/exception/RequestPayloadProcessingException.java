package com.accenture.lambda.exception;

public class RequestPayloadProcessingException extends RuntimeException {

    public RequestPayloadProcessingException(final String message) {
        super(message);
    }

    public RequestPayloadProcessingException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public RequestPayloadProcessingException(final Throwable cause) {
        super(cause);
    }
}
