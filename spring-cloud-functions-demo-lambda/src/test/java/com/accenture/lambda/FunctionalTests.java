package com.accenture.lambda;

import static org.assertj.core.api.Assertions.assertThat;

import com.accenture.lambda.rest.dto.responses.HealthCheckResponse;
import java.util.function.Supplier;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.function.context.FunctionCatalog;
import org.springframework.cloud.function.context.test.FunctionalSpringBootTest;

@FunctionalSpringBootTest
class FunctionalTests {

    @Autowired
    private FunctionCatalog catalog;

    @Test
    void healthcheck() {
        // arrange
        final Supplier<HealthCheckResponse> supplier = catalog.lookup(Supplier.class, "healthcheck");

        // act
        final HealthCheckResponse result = supplier.get();

        // assert
        assertThat(result.getMessage()).isEqualTo("Serverless API is up and running");
        assertThat(result.getVersion()).isEqualTo("1.0-SNAPSHOT");
        assertThat(result.getServertime()).isNotNull();
        assertThat(result.getServertime()).isNotBlank();
        assertThat(result.getServertime()).isNotEmpty();
    }
}
