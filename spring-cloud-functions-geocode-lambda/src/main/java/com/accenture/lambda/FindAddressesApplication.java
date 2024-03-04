package com.accenture.lambda;

import com.accenture.lambda.rest.dto.response.AddressResultsResponse;
import com.accenture.lambda.rest.request.FindAddressesPayload;
import com.accenture.lambda.service.BackendService;
import com.accenture.lambda.service.FindAddressService;
import com.accenture.lambda.service.models.FindAddressCandidatesResponse;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author vincent.renders@accenture.com
 * @since 02/03/2024
 */
@Slf4j
@SpringBootApplication
public class FindAddressesApplication {

    @Bean
    public Function<FindAddressesPayload, AddressResultsResponse> findAddresses() {
        return matchAddressPayload -> {
            log.info("POST /findAddresses called");

            final BackendService backendService = new BackendService();
            final FindAddressCandidatesResponse backendResponse =
                    backendService.findAddressCandidates(matchAddressPayload);

            final FindAddressService apiService = new FindAddressService();
            final AddressResultsResponse apiResponse = apiService.findAddressesResponseMapping(backendResponse);

            log.info("POST /findAddresses call ends");
            return apiResponse;
        };
    }

    public static void main(String[] args) {
        // empty unless running locally or using Custom runtime at which point it should include
        // SpringApplication.run(FindAddressesApplication.class, args);
    }
}
