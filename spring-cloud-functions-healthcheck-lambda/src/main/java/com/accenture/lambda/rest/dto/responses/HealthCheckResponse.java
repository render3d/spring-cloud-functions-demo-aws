package com.accenture.lambda.rest.dto.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class HealthCheckResponse {

    @JsonProperty("message")
    private final String message = "Serverless API is up and running";

    @JsonProperty("version")
    private final String version = "1.0-SNAPSHOT";

    @JsonProperty("servertime")
    private final String serverTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
}
