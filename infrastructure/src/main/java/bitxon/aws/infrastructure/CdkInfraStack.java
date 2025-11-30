package bitxon.aws.infrastructure;

import software.amazon.awscdk.Duration;
import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.apigateway.IntegrationResponse;
import software.amazon.awscdk.services.apigateway.LambdaIntegration;
import software.amazon.awscdk.services.apigateway.MethodResponse;
import software.amazon.awscdk.services.apigateway.RestApi;
import software.amazon.awscdk.services.dynamodb.Attribute;
import software.amazon.awscdk.services.dynamodb.AttributeType;
import software.amazon.awscdk.services.dynamodb.BillingMode;
import software.amazon.awscdk.services.dynamodb.Table;
import software.amazon.awscdk.services.lambda.*;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.logs.LogGroup;
import software.amazon.awscdk.services.logs.RetentionDays;
import software.constructs.Construct;

import java.util.List;
import java.util.Map;

public class CdkInfraStack extends Stack {

    public CdkInfraStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        // -------------------------------------- DynamoDB -------------------------------------
        var dynamoDbTable = Table.Builder.create(this, "OrdersTable")
            .tableName("Orders")
            .partitionKey(Attribute.builder().name("id").type(AttributeType.STRING).build())
            .billingMode(BillingMode.PAY_PER_REQUEST)
            .removalPolicy(RemovalPolicy.DESTROY)
            .build();

        // ------------------------------------ Plain Java -------------------------------------
        var funcPlainName = "order-function-plain";
        var funcPlain = Function.Builder.create(this, funcPlainName)
            .functionName(funcPlainName)
            .code(Code.fromAsset("../order-function-plain/build/libs/order-function-plain-1.0-SNAPSHOT-all.jar"))
            .memorySize(512)
            .timeout(Duration.seconds(20))
            .handler("bitxon.aws.plain.OrderHandler::handleRequest")
            .runtime(Runtime.JAVA_21)
            .environment(Map.of("TABLE_NAME", dynamoDbTable.getTableName()))
            .logGroup(logGroup(this, funcPlainName))
            .build();
        dynamoDbTable.grantWriteData(funcPlain);

        // -------------------------------------- Quarkus --------------------------------------
        var funQuarkusName = "order-function-quarkus";
        var funcQuarkus = Function.Builder.create(this, funQuarkusName)
            .functionName(funQuarkusName)
            .code(Code.fromAsset("../order-function-quarkus/build/function.zip"))
            .memorySize(512)
            .timeout(Duration.seconds(20))
            .handler("io.quarkus.amazon.lambda.runtime.QuarkusStreamHandler::handleRequest")
            .runtime(Runtime.JAVA_21)
            .environment(Map.of("TABLE_NAME", dynamoDbTable.getTableName()))
            .logGroup(logGroup(this, funQuarkusName))
            .build();
        dynamoDbTable.grantWriteData(funcQuarkus);

        // -------------------------------------- Micronaut --------------------------------------
        var funcMicronautName = "order-function-micronaut";
        var funcMicronaut = Function.Builder.create(this, funcMicronautName)
            .functionName(funcMicronautName)
            .code(Code.fromAsset("../order-function-micronaut/build/libs/order-function-micronaut-1.0-SNAPSHOT-all.jar"))
            .memorySize(512)
            .timeout(Duration.seconds(20))
            .handler("bitxon.aws.micronaut.OrderHandler")
            .runtime(Runtime.JAVA_21)
            .environment(Map.of(
                "TABLE_NAME", dynamoDbTable.getTableName(),
                "DISABLE_SIGNAL_HANDLERS", "true"
            ))
            .logGroup(logGroup(this, funcMicronautName))
            .build();
        dynamoDbTable.grantWriteData(funcMicronaut);

        // -------------------------------------- Spring --------------------------------------
        var funSpringName = "order-function-spring";
        var funcSpring = Function.Builder.create(this, funSpringName)
            .functionName(funSpringName)
            .code(Code.fromAsset("../order-function-spring/build/libs/order-function-spring-1.0-SNAPSHOT-aws.jar"))
            .memorySize(512)
            .timeout(Duration.seconds(20))
            .handler("org.springframework.cloud.function.adapter.aws.FunctionInvoker::handleRequest")
            .runtime(Runtime.JAVA_21)
            .environment(Map.of("TABLE_NAME", dynamoDbTable.getTableName()))
            .logGroup(logGroup(this, funSpringName))
            .build();
        dynamoDbTable.grantWriteData(funcSpring);

        // -------------------------------------- Python --------------------------------------
        var funPythonName = "order-function-python";
        var funcPython = Function.Builder.create(this, funPythonName)
            .functionName(funPythonName)
            .code(Code.fromAsset("../order-function-python"))
            .memorySize(512)
            .timeout(Duration.seconds(20))
            .handler("app.lambda_handler")
            .runtime(Runtime.PYTHON_3_13)
            .environment(Map.of("TABLE_NAME", dynamoDbTable.getTableName()))
            .logGroup(logGroup(this, funPythonName))
            .build();
        dynamoDbTable.grantWriteData(funcPython);


        // ---------------------------------------- Api ----------------------------------------
        var api = RestApi.Builder.create(this, "order").build();
        var orderResource = api.getRoot().addResource("order");
        // Non-Proxy APIs
        orderResource
            .addResource("plain")
            .addMethod("POST", integrationNonProxy(funcPlain))
            .addMethodResponse(MethodResponse.builder().statusCode("200").build());
        orderResource
            .addResource("quarkus")
            .addMethod("POST", integrationNonProxy(funcQuarkus))
            .addMethodResponse(MethodResponse.builder().statusCode("200").build());
        orderResource
            .addResource("micronaut")
            .addMethod("POST", integrationNonProxy(funcMicronaut))
            .addMethodResponse(MethodResponse.builder().statusCode("200").build());
        orderResource
            .addResource("spring")
            .addMethod("POST", integrationNonProxy(funcSpring))
            .addMethodResponse(MethodResponse.builder().statusCode("200").build());
        orderResource
            .addResource("python")
            .addMethod("POST", integrationNonProxy(funcPython))
            .addMethodResponse(MethodResponse.builder().statusCode("200").build());
    }

    // ---------------------------------- Helper Utils -----------------------------------------

    private static LambdaIntegration integrationNonProxy(IFunction function) {
        return LambdaIntegration.Builder.create(function)
            .proxy(false)
            .integrationResponses(
                List.of(IntegrationResponse.builder().statusCode("200").build()))
            .build();
    }

    private static LogGroup logGroup(Construct scope, String name) {
        return LogGroup.Builder.create(scope, "%s-logs".formatted(name))
            .logGroupName("/aws/lambda/%s".formatted(name))
            .retention(RetentionDays.ONE_WEEK)
            .removalPolicy(RemovalPolicy.DESTROY)
            .build();
    }

}
