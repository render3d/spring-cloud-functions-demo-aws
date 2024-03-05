---
title: Intro to Spring Cloud Functions
---

# Intro to Spring Cloud Functions

#### Functional Programming for Serverless Spring

<aside class="notes">
  N/A
</aside>

---

### Overview

- ***What:*** Spring Cloud Function (SCF) is a project that promotes the implementation of business logic via functions

<aside class="notes">
  N/A
</aside>

---

### Overview

- ***What:*** Spring Cloud Function (SCF) is a project that promotes the implementation of business logic via functions
- ***Why:*** To decouple the development lifecycle of business logic from any specific runtime target so that the same code can run as a web endpoint, a stream processor, or a task

<aside class="notes">
  N/A
</aside>

---

### Overview

- ***What:*** Spring Cloud Function (SCF) is a project that promotes the implementation of business logic via functions
- ***Why:*** To decouple the development lifecycle of business logic from any specific runtime target so that the same code can run as a web endpoint, a stream processor, or a task
- ***How:*** By providing a uniform programming model across serverless providers, as well as the ability to run standalone (locally or in a PaaS)

<aside class="notes">
  N/A
</aside>

---

### Implementation

SCF builds on the 3 core functional interfaces available since Java 8:

- `Supplier<O>`
- `Function<I,O>`
- `Consumer<I>`

Collectively, these are referred to within SCF as "functional beans"

<aside class="notes">
  N/A
</aside>

---

### Applications

SCF instruments Java functions with additional features to be utilised in variety of execution contexts:

- **REST support:** allows functions to be exposed as HTTP endpoints
- **Event-driven microservices:** functions can be integrated with cloud events e.g. EventBridge
- **Streaming message handler:** send or receive messages from a broker (such as *Kafka*) by leveraging integrations with the *Spring Cloud Stream* project

<aside class="notes">
  There may be more but today's session will explore the first two from the list above
</aside>

---

# DEMO

<aside class="notes">

```java
@Component
public class UpperCaseFunction implements Function<String, String> {
  @Override
  public String apply(String value) {
    return value.toUpperCase();
  }
}
```

```java
@SpringBootApplication
public class SimpleFunctionAppApplication {
  @Bean
  public Function<String, String> uppercase() {
    return value -> value.toUpperCase();
  }

  public static void main(String[] args) {
    SpringApplication.run(SimpleFunctionAppApplication.class, args);
  }
}
```

http://localhost:8080/swagger-ui/index.html

</aside>

---

### SCF Advantages

- In my experience, does very well to facilitate integrations allowing the developer to focus on business logic
- Enables Spring Boot features (autoconfiguration, dependency injection, metrics, etc.) on serverless providers
- SCF can be public cloud **vendor agnostic**
  - adapters for MAG (plus others) serverless service providers exist already
- Supports function composition

<aside class="notes">
  N/A
</aside>

---

### Closing Remarks

- [Demo Repo](https://github.com/render3d/spring-cloud-functions-demo-aws) contains:
  - SCF x2 following `@Bean` style
    - [`GET /healthcheck` lambda](spring-cloud-functions-healthcheck-lambda/src/main/java/com/accenture/lambda/HealthCheckApplication.java)
    - [`POST /findAddresses` lambda](spring-cloud-functions-geocode-lambda/src/main/java/com/accenture/lambda/FindAddressesApplication.java)
  - SCF x1 following `@Component` style
    - [EventBridge-triggered lambda](spring-cloud-functions-eventbridge-lambda/src/main/java/com/accenture/lambda/functions/EventBridgeFunction.java)
  - [Small CDK demo stack](spring-cloud-functions-demo-infrastructure/src/main/java/com/accenture/cdk/SpringCloudFunctionsDemoInfrastructureStack.java) to demonstrate necessary lambda configuration
  - *Some* [example SCF unit tests](spring-cloud-functions-healthcheck-lambda/src/test/java/com/accenture/lambda)

<aside class="notes">
  N/A
</aside>

---

### Closing Remarks

- Unsure if the best SCF features/practices have been applied
- Open to feedback in the form of code review, PR, written etc.

<aside class="notes">
  N/A
</aside>

---

# END
