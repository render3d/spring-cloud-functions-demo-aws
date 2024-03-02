package com.accenture.lambda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestSpringCloudFunctionsDemoApplication {

    public static void main(String[] args) {
        SpringApplication.from(SpringCloudFunctionsDemoApplication::main)
                .with(TestSpringCloudFunctionsDemoApplication.class)
                .run(args);
    }
}
