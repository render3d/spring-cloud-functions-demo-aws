package com.accenture.cdk;

import com.accenture.cdk.resource.SnapStartLambda;
import java.util.Collections;
import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.CfnOutputProps;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.aws_apigatewayv2_integrations.HttpLambdaIntegration;
import software.amazon.awscdk.aws_apigatewayv2_integrations.HttpLambdaIntegrationProps;
import software.amazon.awscdk.services.apigatewayv2.AddRoutesOptions;
import software.amazon.awscdk.services.apigatewayv2.HttpApi;
import software.amazon.awscdk.services.apigatewayv2.HttpApiProps;
import software.amazon.awscdk.services.apigatewayv2.HttpMethod;
import software.amazon.awscdk.services.apigatewayv2.PayloadFormatVersion;
import software.amazon.awscdk.services.lambda.Function;
import software.constructs.Construct;

public class SpringCloudFunctionsDemoInfrastructureStack extends Stack {
    public SpringCloudFunctionsDemoInfrastructureStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public SpringCloudFunctionsDemoInfrastructureStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        // Healthcheck Lambda
        final Function healthcheckLambda = new SnapStartLambda.Builder()
                .withStack(this)
                .withModuleName("spring-cloud-functions-healthcheck-lambda")
                .withFunctionName("healthcheck")
                .build();

        LambdaDeploymentGroup.Builder.create(this, "DeploymentGroup")
                .alias(alias)
                .deploymentConfig(LambdaDeploymentConfig.LINEAR_10_PERCENT_EVERY_1_MINUTE)
                .build();

        // API Gateway
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
                        healthcheckLambda,
                        HttpLambdaIntegrationProps.builder()
                                .payloadFormatVersion(PayloadFormatVersion.VERSION_2_0)
                                .build()))
                .build());

        // Outputs
        new CfnOutput(
                this,
                "HttApi",
                CfnOutputProps.builder()
                        .description("Healthcheck endpoint for Http Api")
                        .value(String.format("%s/healthcheck", httpApi.getApiEndpoint()))
                        .build());
    }
}
