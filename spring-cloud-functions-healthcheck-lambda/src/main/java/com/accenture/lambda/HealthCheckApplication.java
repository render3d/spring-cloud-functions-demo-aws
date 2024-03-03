package com.accenture.lambda;

import com.accenture.lambda.rest.dto.responses.HealthCheckResponse;
import java.util.function.Supplier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author vincent.renders@accenture.com
 * @since 01/03/2024
 */
@Slf4j
@SpringBootApplication
public class HealthCheckApplication {

    @Bean
    public Supplier<HealthCheckResponse> healthcheck() {
        return HealthCheckResponse::new;
    }

    public static void main(String[] args) {
        SpringApplication.run(HealthCheckApplication.class, args);
    }
}
