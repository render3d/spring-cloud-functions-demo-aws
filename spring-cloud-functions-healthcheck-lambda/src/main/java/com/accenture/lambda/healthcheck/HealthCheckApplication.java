package com.accenture.lambda.healthcheck;

import com.accenture.lambda.healthcheck.rest.dto.responses.HealthCheckResponse;
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

    //    @Bean
    //    public Function<MatchAddressPayload, MatchAddressResponse> matchAddress() {
    //        return matchAddressPayload -> {
    //            log.info("POST /matchAddress called with payload: {}");
    //
    //            final BackendService backendService = new BackendService();
    //            final FindAddressCandidatesResponse backendResponse =
    //                    backendService.findAddressCandidates(matchAddressPayload);
    //
    //            final ServerlessApiPocService apiService = new ServerlessApiPocService();
    //            final MatchAddressResponse apiResponse = apiService.matchAddressResponseMapping(backendResponse);
    //
    //            log.debug("POST /matchAddress call ends");
    //            return apiResponse;
    //        };
    //    }

    public static void main(String[] args) {
        SpringApplication.run(HealthCheckApplication.class, args);
    }
}
