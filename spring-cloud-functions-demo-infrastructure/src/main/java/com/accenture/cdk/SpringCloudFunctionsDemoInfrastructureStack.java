package com.accenture.cdk;

import com.accenture.cdk.resource.DemoApiGateway;
import com.accenture.cdk.resource.EventBridgeTimerRule;
import com.accenture.cdk.resource.SnapStartLambda;
import java.util.HashMap;
import java.util.Map;
import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.CfnOutputProps;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.apigatewayv2.HttpApi;
import software.amazon.awscdk.services.lambda.Function;
import software.constructs.Construct;

public class SpringCloudFunctionsDemoInfrastructureStack extends Stack {

    public SpringCloudFunctionsDemoInfrastructureStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        // Healthcheck Lambda
        final Function healthcheckLambda = SnapStartLambda.Factory.createFunction(
                this, "healthcheck", "spring-cloud-functions-healthcheck-lambda");

        // Address Search Lambda
        final Function findAddressesLambda =
                SnapStartLambda.Factory.createFunction(this, "findAddresses", "spring-cloud-functions-geocode-lambda");

        // API Gateway
        final HttpApi httpApi =
                DemoApiGateway.Factory.createDemoApiGateway(this, healthcheckLambda, findAddressesLambda);

        // EventBridge-triggered Lambda
        Map<String, String> envVars = new HashMap<>();
        envVars.put("API_ROOT_URL", httpApi.getApiEndpoint());
        envVars.put("HEALTHCHECK_PATH", String.format("/%s", healthcheckLambda.getFunctionName()));
        envVars.put("GEOCODE_PATH", String.format("/%s", findAddressesLambda.getFunctionName()));

        final Function eventBridgeLambda = SnapStartLambda.Factory.createFunction(
                this, "EventBridgeFunction", "spring-cloud-functions-eventbridge-lambda", envVars);

        EventBridgeTimerRule.Factory.createTimedEvent(this, eventBridgeLambda);

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
