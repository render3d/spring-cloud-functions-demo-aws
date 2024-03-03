package com.accenture.lambda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestHealthCheckApplication {

    public static void main(String[] args) {
        SpringApplication.from(HealthCheckApplication::main)
                .with(TestHealthCheckApplication.class)
                .run(args);
    }
}
