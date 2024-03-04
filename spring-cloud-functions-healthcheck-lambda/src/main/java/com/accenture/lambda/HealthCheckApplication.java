package com.accenture.lambda;

import com.accenture.lambda.rest.dto.responses.HealthCheckResponse;
import java.util.function.Supplier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author vincent.renders@accenture.com
 * @since 01/03/2024
 */
@SpringBootApplication
public class HealthCheckApplication {

    @Bean
    public Supplier<HealthCheckResponse> healthcheck() {
        return HealthCheckResponse::new;
    }

    public static void main(String[] args) {
        // empty unless running locally or using Custom runtime at which point it should include
        //  SpringApplication.run(HealthCheckApplication.class, args);
    }
}
