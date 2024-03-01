# Spring Cloud Functions Demo with AWS

## Context

This is a demo project to introduce Spring Cloud Functions and demonstrate their use with AWS Lambda for the following use cases:

- Serverless REST APIs by enabling Spring Boot features to expose functions as HTTP endpoints
- Event-driven microservices with AWS EventBridge integration
- SnapStart for improved cold-starts and overall performance gains

## Modules

This project uses the following modules to organise Java code:

1. [Lambda Module](spring-cloud-functions-demo-lambda/README.md) - Spring Cloud Function source code for the Lambda runtime
2. [Infrastructure Module](spring-cloud-functions-demo-infrastructure/README.md) - the AWS CDK stack used for the demo

# References

- [Spring Cloud Function Docs](https://docs.spring.io/spring-cloud-function/docs/current/reference/html/spring-cloud-function.html)
