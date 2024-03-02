package com.accenture.cdk;

import java.util.Collections;
import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.CfnOutputProps;
import software.amazon.awscdk.Duration;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.aws_apigatewayv2_integrations.HttpLambdaIntegration;
import software.amazon.awscdk.aws_apigatewayv2_integrations.HttpLambdaIntegrationProps;
import software.amazon.awscdk.services.apigatewayv2.AddRoutesOptions;
import software.amazon.awscdk.services.apigatewayv2.HttpApi;
import software.amazon.awscdk.services.apigatewayv2.HttpApiProps;
import software.amazon.awscdk.services.apigatewayv2.HttpMethod;
import software.amazon.awscdk.services.apigatewayv2.PayloadFormatVersion;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.FunctionProps;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.lambda.SnapStartConf;
import software.constructs.Construct;

public class SpringCloudFunctionsDemoInfrastructureStack extends Stack {
    public SpringCloudFunctionsDemoInfrastructureStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public SpringCloudFunctionsDemoInfrastructureStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        // The code that defines your stack goes here

        final Function healthcheck = new Function(
                this,
                "spring-cloud-functions-demo-lambda",
                FunctionProps.builder()
                        .runtime(Runtime.JAVA_17)
                        .code(
                                Code.fromAsset(
                                        "../spring-cloud-functions-demo-lambda/target/spring-cloud-functions-demo-lambda-1.0-SNAPSHOT-aws.jar"))
                        .handler("org.springframework.cloud.function.adapter.aws.FunctionInvoker::handleRequest")
                        .memorySize(1024)
                        .snapStart(SnapStartConf.ON_PUBLISHED_VERSIONS)
                        .timeout(Duration.seconds(10))
                        .build());

        HttpApi httpApi = new HttpApi(
                this,
                "demo-api",
                HttpApiProps.builder()
                        .apiName("spring-cloud-function-serverless-api")
                        .build());

        httpApi.addRoutes(AddRoutesOptions.builder()
                .path("/healthcheck")
                .methods(Collections.singletonList(HttpMethod.GET))
                .integration(new HttpLambdaIntegration(
                        "healthcheck",
                        healthcheck,
                        HttpLambdaIntegrationProps.builder()
                                .payloadFormatVersion(PayloadFormatVersion.VERSION_2_0)
                                .build()))
                .build());

        new CfnOutput(
                this,
                "HttApi",
                CfnOutputProps.builder()
                        .description("Healthcheck endpoint for Http Api")
                        .value(String.format("%s/healthcheck", httpApi.getApiEndpoint()))
                        .build());
    }
}
