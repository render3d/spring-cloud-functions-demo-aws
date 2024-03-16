package com.accenture.cdk.resource;

import java.util.Collections;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.aws_apigatewayv2_integrations.HttpLambdaIntegration;
import software.amazon.awscdk.aws_apigatewayv2_integrations.HttpLambdaIntegrationProps;
import software.amazon.awscdk.services.apigatewayv2.AddRoutesOptions;
import software.amazon.awscdk.services.apigatewayv2.HttpApi;
import software.amazon.awscdk.services.apigatewayv2.HttpApiProps;
import software.amazon.awscdk.services.apigatewayv2.HttpMethod;
import software.amazon.awscdk.services.apigatewayv2.PayloadFormatVersion;
import software.amazon.awscdk.services.lambda.Function;

public class DemoApiGatewayFactory {

    private DemoApiGatewayFactory() {}

    public static HttpApi createDemoApiGateway(
            final Stack stack, final Function healthcheckLambda, final Function findAddressesLambda) {
        final HttpApi httpApi = new HttpApi(
                stack,
                "demo-api",
                HttpApiProps.builder()
                        .apiName("spring-cloud-function-serverless-api")
                        .build());

        httpApi.addRoutes(AddRoutesOptions.builder()
                .path("/healthcheck")
                .methods(Collections.singletonList(HttpMethod.GET))
                .integration(new HttpLambdaIntegration(
                        "healthcheckFunction",
                        healthcheckLambda,
                        HttpLambdaIntegrationProps.builder()
                                .payloadFormatVersion(PayloadFormatVersion.VERSION_2_0)
                                .build()))
                .build());

        httpApi.addRoutes(AddRoutesOptions.builder()
                .path("/findAddresses")
                .methods(Collections.singletonList(HttpMethod.POST))
                .integration(new HttpLambdaIntegration(
                        "findAddresses",
                        findAddressesLambda,
                        HttpLambdaIntegrationProps.builder()
                                .payloadFormatVersion(PayloadFormatVersion.VERSION_2_0)
                                .build()))
                .build());

        return httpApi;
    }
}
