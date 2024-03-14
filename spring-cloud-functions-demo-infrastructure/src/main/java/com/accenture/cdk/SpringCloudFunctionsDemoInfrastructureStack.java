package com.accenture.cdk;

import com.accenture.cdk.resource.DemoApiGatewayFactory;
import com.accenture.cdk.resource.EventBridgeRuleFactory;
import com.accenture.cdk.resource.SnapStartLambdaFactory;
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
        final Function healthcheckLambda =
                SnapStartLambdaFactory.createFunction(this, "healthcheck", "spring-cloud-functions-healthcheck-lambda");

        // Address Search Lambda
        final Function findAddressesLambda =
                SnapStartLambdaFactory.createFunction(this, "findAddresses", "spring-cloud-functions-geocode-lambda");

        // API Gateway
        final HttpApi httpApi =
                DemoApiGatewayFactory.createDemoApiGateway(this, healthcheckLambda, findAddressesLambda);

        // EventBridge-triggered Lambda
        Map<String, String> envVars = new HashMap<>();
        envVars.put("API_ROOT_URL", httpApi.getApiEndpoint());
        envVars.put("HEALTHCHECK_PATH", String.format("/%s", healthcheckLambda.getFunctionName()));
        envVars.put("GEOCODE_PATH", String.format("/%s", findAddressesLambda.getFunctionName()));

        final Function eventBridgeLambda = SnapStartLambdaFactory.createFunction(
                this, "EventBridgeFunction", "spring-cloud-functions-eventbridge-lambda", envVars);

        EventBridgeRuleFactory.createTimedEvent(this, eventBridgeLambda);

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
