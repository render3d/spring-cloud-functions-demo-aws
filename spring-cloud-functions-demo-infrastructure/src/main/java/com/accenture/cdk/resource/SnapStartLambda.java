package com.accenture.cdk.resource;

import java.util.HashMap;
import java.util.Map;
import software.amazon.awscdk.Duration;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.codedeploy.LambdaDeploymentConfig;
import software.amazon.awscdk.services.codedeploy.LambdaDeploymentGroup;
import software.amazon.awscdk.services.lambda.Alias;
import software.amazon.awscdk.services.lambda.Architecture;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.FunctionProps;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.lambda.SnapStartConf;

public class SnapStartLambda {

    private SnapStartLambda() {}

    public static class Factory {

        private Factory() {}

        public static Function createFunction(Stack stack, String functionName, String moduleName) {
            return createFunction(stack, functionName, moduleName, new HashMap<>());
        }

        public static Function createFunction(
                Stack stack, String functionName, String moduleName, Map<String, String> envVars) {
            final String assetPath = String.format("../%s/target/%s-1.0-SNAPSHOT-aws.jar", moduleName, moduleName);

            final FunctionProps props = FunctionProps.builder()
                    .functionName(moduleName)
                    .runtime(Runtime.JAVA_17)
                    .architecture(Architecture.X86_64) // SnapStart is not currently supported on Arm_64
                    .code(Code.fromAsset(assetPath))
                    .handler("org.springframework.cloud.function.adapter.aws.FunctionInvoker::handleRequest")
                    .memorySize(1024)
                    .snapStart(SnapStartConf.ON_PUBLISHED_VERSIONS)
                    .timeout(Duration.seconds(15))
                    .environment(envVars)
                    .build();

            final Function function = new Function(stack, functionName, props);

            // used to make sure each CDK synthesis produces a different Version (versioning required for SnapStart)
            final String aliasName = String.format("%sAlias", functionName);
            final Alias alias = Alias.Builder.create(stack, aliasName)
                    .aliasName(aliasName)
                    .version(function.getCurrentVersion())
                    .build();

            final String deploymentGroupId = String.format("%sDeploymentGroup", functionName);
            LambdaDeploymentGroup.Builder.create(stack, deploymentGroupId)
                    .alias(alias)
                    .deploymentConfig(LambdaDeploymentConfig.ALL_AT_ONCE)
                    .build();

            return function;
        }
    }
}
