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
import software.amazon.awscdk.services.events.Rule;
import software.amazon.awscdk.services.events.Schedule;
import software.amazon.awscdk.services.events.targets.LambdaFunction;
import software.amazon.awscdk.services.lambda.Function;
import software.constructs.Construct;

public class SpringCloudFunctionsDemoInfrastructureStack extends Stack {

    public SpringCloudFunctionsDemoInfrastructureStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        // Healthcheck Lambda
        final String healthcheckFunctionName = "healthcheck";
        final Function healthcheckLambda = new SnapStartLambda.Builder()
                .withStack(this)
                .withModuleName("spring-cloud-functions-healthcheck-lambda")
                .withFunctionName(healthcheckFunctionName)
                .build();

        // Address Search Lambda
        final String geocodeFunctionName = "findAddresses";
        final Function findAddressesLambda = new SnapStartLambda.Builder()
                .withStack(this)
                .withModuleName("spring-cloud-functions-geocode-lambda")
                .withFunctionName(geocodeFunctionName)
                .build();

        // API Gateway
        HttpApi httpApi = new HttpApi(
                this,
                "demo-api",
                HttpApiProps.builder()
                        .apiName("spring-cloud-function-serverless-api")
                        .build());

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

        // EventBridge-triggered Lambda
        final Function eventBridgeLambda = new SnapStartLambda.Builder()
                .withStack(this)
                .withModuleName("spring-cloud-functions-eventbridge-lambda")
                .withFunctionName("EventBridgeFunction")
                .build();

        Rule.Builder.create(this, "TimerRule")
                .ruleName("EventBridgeTimerTrigger")
                .description("Scheduled event to trigger lambda")
                /*
                   The cron expression below triggers the lambda according to the following timings:
                       - every 5 minutes starting at minute 20 of the hour ("20/5")
                       - every hour of the day (the first "*")
                       - every day of the month (the second "*")
                       - every month (the third "*")
                       - every year (the last "*")
                */
                .schedule(Schedule.expression("cron(20/5 * * * ? *)"))
                .targets(Collections.singletonList(
                        LambdaFunction.Builder.create(eventBridgeLambda).build()))
                .build();

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
