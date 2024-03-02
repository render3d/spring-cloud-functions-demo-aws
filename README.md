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

## Deployment

Execute the following terminal commands, in order and from the repository root, to deploy the functions to AWS:

```bash
cd spring-cloud-functions-demo-lambda
mvn clean package
cd ../spring-cloud-functions-demo-infrastructure
cdk synth
cdk deploy
```

## References

- [Spring Cloud Function Docs](https://docs.spring.io/spring-cloud-function/docs/current/reference/html/spring-cloud-function.html)
- [Spring Cloud Function with SnapStart YouTube Tutorial](https://www.youtube.com/embed/isS6m6aj_Ak?si=2vo4k1SZIyLX-I4B)
