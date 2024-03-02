package com.accenture.lambda.healthcheck;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

@SpringBootTest(classes = HealthCheckApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
class HealthCheckApplicationTests {

    @Autowired
    private TestRestTemplate rest;

    @Test
    void healthcheck() throws URISyntaxException {
        // arrange
        final RequestEntity<Void> request =
                RequestEntity.get(new URI("/healthcheck")).build();

        // act
        final ResponseEntity<String> result = this.rest.exchange(request, String.class);

        // assert
        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(result.getBody())
                .contains(
                        "\"message\":\"Serverless API is up and running\",\"version\":\"1.0-SNAPSHOT\",\"servertime\":");
    }
}
