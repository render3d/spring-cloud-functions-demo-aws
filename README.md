# Spring Cloud Functions Demo with AWS

## Context

This is a demo project to introduce Spring Cloud Functions and demonstrate their use with AWS Lambda for the following use cases:

- Serverless REST APIs by enabling Spring Boot features to expose functions as HTTP endpoints
- Event-driven microservices with AWS EventBridge integration
- SnapStart for improved cold-starts and overall performance gains

## Modules

This project uses Maven modules to follow a lambda-per-module pattern.
For a small project with a small number of functions, a single JAR containing all the functions can be uploaded to AWS Lambda and the function that you want to run is specified.
For a larger project, each function may have its own module so that it can be deployed as a single JAR. So if you have 20 Lambda functions, you have 20 modules, and you deploy 20 JAR files.

To that end, this project organises Java code into the following modules:

1. [Infrastructure Module](spring-cloud-functions-demo-infrastructure/README.md) - the AWS CDK stack used for the demo
2. [Healthcheck Lambda Module](spring-cloud-functions-healthcheck-lambda/README.md) - source code for the lambda mapped to the `GET /healthcheck` endpoint
3. [Geocode Lambda Module](spring-cloud-functions-geocode-lambda/README.md) - source code for the lambda mapped to the `POST /findAddresses` endpoint

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
- [Activating and managing Lambda SnapStart](https://docs.aws.amazon.com/lambda/latest/dg/snapstart-activate.html)
