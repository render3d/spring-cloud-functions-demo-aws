package com.accenture.lambda.rest.dto.responses;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.Getter;

@Getter
public class HealthCheckResponse {
    String message = "Serverless API is up and running";
    String version = "1.0-SNAPSHOT";
    String servertime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
}
