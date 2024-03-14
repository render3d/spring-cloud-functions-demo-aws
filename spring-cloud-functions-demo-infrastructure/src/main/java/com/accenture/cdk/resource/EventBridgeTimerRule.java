package com.accenture.cdk.resource;

import java.util.Collections;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.events.Rule;
import software.amazon.awscdk.services.events.Schedule;
import software.amazon.awscdk.services.events.targets.LambdaFunction;
import software.amazon.awscdk.services.lambda.Function;

public class EventBridgeTimerRule {

    private EventBridgeTimerRule() {}

    public static class Factory {

        private Factory() {}

        public static Rule createTimedEvent(final Stack stack, final Function eventBridgeLambda) {
            return Rule.Builder.create(stack, "TimerRule")
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
        }
    }
}
