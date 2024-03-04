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
import software.constructs.Construct;

public class SnapStartLambda extends Function {

    private SnapStartLambda(final Construct scope, final String id, final FunctionProps props) {
        super(scope, id, props);
    }

    public static class Builder {

        private Stack stack;
        private String functionName;
        private String moduleName;
        private Map<String, String> envVars;

        public Builder withStack(final Stack stack) {
            this.stack = stack;
            return this;
        }

        public Builder withFunctionName(final String functionName) {
            this.functionName = functionName;
            if (this.envVars == null) {
                this.envVars = new HashMap<>();
            }
            this.envVars.put("FUNCTION_NAME", functionName);
            return this;
        }

        public Builder withModuleName(final String moduleName) {
            this.moduleName = moduleName;
            return this;
        }

        public Builder withEnvironmentVariables(final Map<String, String> envVarsMap) {
            if (this.envVars == null) {
                this.envVars = new HashMap<>();
            }
            this.envVars.putAll(envVarsMap);
            return this;
        }

        public Builder withEnvironmentVariable(final String key, final String value) {
            if (this.envVars == null) {
                this.envVars = new HashMap<>();
            }
            this.envVars.put(key, value);
            return this;
        }

        public Function build() {
            final String assetPath =
                    String.format("../%s/target/%s-1.0-SNAPSHOT-aws.jar", this.moduleName, this.moduleName);

            final FunctionProps props = FunctionProps.builder()
                    .functionName(this.moduleName)
                    .runtime(Runtime.JAVA_17)
                    .architecture(Architecture.X86_64) // SnapStart is not currently supported on Arm_64
                    .code(Code.fromAsset(assetPath))
                    .handler("org.springframework.cloud.function.adapter.aws.FunctionInvoker::handleRequest")
                    .memorySize(512)
                    .snapStart(SnapStartConf.ON_PUBLISHED_VERSIONS)
                    .timeout(Duration.seconds(15))
                    .environment(this.envVars)
                    .build();

            final Function function = new SnapStartLambda(this.stack, this.functionName, props);

            // used to make sure each CDK synthesis produces a different Version (versioning required for SnapStart)
            final String aliasName = String.format("%sAlias", functionName);
            final Alias alias = Alias.Builder.create(this.stack, aliasName)
                    .aliasName(aliasName)
                    .version(function.getCurrentVersion())
                    .build();

            final String deploymentGroupId = String.format("%sDeploymentGroup", functionName);
            LambdaDeploymentGroup.Builder.create(this.stack, deploymentGroupId)
                    .alias(alias)
                    .deploymentConfig(LambdaDeploymentConfig.ALL_AT_ONCE)
                    .build();

            return function;
        }
    }
}
