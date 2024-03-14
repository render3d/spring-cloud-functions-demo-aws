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

        final String healthcheckFunctionName = healthcheckLambda.getFunctionName();
        final String healthcheckPath = String.format("/%s", healthcheckFunctionName);
        httpApi.addRoutes(AddRoutesOptions.builder()
                .path(healthcheckPath)
                .methods(Collections.singletonList(HttpMethod.GET))
                .integration(new HttpLambdaIntegration(
                        healthcheckFunctionName,
                        healthcheckLambda,
                        HttpLambdaIntegrationProps.builder()
                                .payloadFormatVersion(PayloadFormatVersion.VERSION_2_0)
                                .build()))
                .build());

        final String geocodeFunctionName = findAddressesLambda.getFunctionName();
        final String geocodePath = String.format("/%s", geocodeFunctionName);
        httpApi.addRoutes(AddRoutesOptions.builder()
                .path(geocodePath)
                .methods(Collections.singletonList(HttpMethod.POST))
                .integration(new HttpLambdaIntegration(
                        geocodeFunctionName,
                        findAddressesLambda,
                        HttpLambdaIntegrationProps.builder()
                                .payloadFormatVersion(PayloadFormatVersion.VERSION_2_0)
                                .build()))
                .build());

        return httpApi;
    }
}
