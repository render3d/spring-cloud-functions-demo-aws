// package com.accenture.cdk;
//
// import java.io.IOException;
// import java.util.HashMap;
// import org.junit.jupiter.api.Test;
// import software.amazon.awscdk.App;
// import software.amazon.awscdk.assertions.Template;
//
// class SpringCloudFunctionsDemoInfrastructureTest {
//
//    @Test
//    void testStack() throws IOException {
//        App app = new App();
//        SpringCloudFunctionsDemoInfrastructureStack stack =
//                new SpringCloudFunctionsDemoInfrastructureStack(app, "test");
//
//        Template template = Template.fromStack(stack);
//
//        template.hasResourceProperties("AWS::SQS::Queue", new HashMap<String, Number>() {
//            {
//                put("VisibilityTimeout", 300);
//            }
//        });
//    }
// }
