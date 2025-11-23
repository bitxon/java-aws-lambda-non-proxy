package bitxon.aws.infrastructure;

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
import software.constructs.Construct;

import java.util.List;
import java.util.Map;

public class CdkInfraStack extends Stack {
    public CdkInfraStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public CdkInfraStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        // -------------------------------------- DynamoDB -------------------------------------
        var dynamoDbTable = Table.Builder.create(this, "OrdersTable")
            .tableName("Orders")
            .partitionKey(Attribute.builder().name("id").type(AttributeType.STRING).build())
            .billingMode(BillingMode.PAY_PER_REQUEST)
            .build();

        // ------------------------------------ Plain Java -------------------------------------
        var funcPlain = Function.Builder.create(this, "order-function-plain")
            .functionName("order-function-plain")
            .code(Code.fromAsset("../order-function-plain/build/libs/order-function-plain-1.0-SNAPSHOT-all.jar"))
            .memorySize(512)
            .handler("bitxon.aws.plain.OrderHandler::handleRequest")
            .runtime(Runtime.JAVA_21)
            .environment(Map.of("TABLE_NAME", dynamoDbTable.getTableName()))
            .build();
        dynamoDbTable.grantWriteData(funcPlain);

        // -------------------------------------- Quarkus --------------------------------------
        var funcQuarkus = Function.Builder.create(this, "order-function-quarkus")
            .functionName("order-function-quarkus")
            .code(Code.fromAsset("../order-function-quarkus/build/function.zip"))
            .memorySize(512)
            .handler("io.quarkus.amazon.lambda.runtime.QuarkusStreamHandler::handleRequest")
            .runtime(Runtime.JAVA_21)
            .environment(Map.of("TABLE_NAME", dynamoDbTable.getTableName()))
            .build();
        dynamoDbTable.grantWriteData(funcQuarkus);

        // -------------------------------------- Micronaut --------------------------------------
        var funcMicronaut = Function.Builder.create(this, "order-function-micronaut")
            .functionName("order-function-micronaut")
            .code(Code.fromAsset("../order-function-micronaut/build/libs/order-function-micronaut-1.0-SNAPSHOT-all.jar"))
            .memorySize(512)
            .handler("bitxon.aws.micronaut.OrderHandler")
            .runtime(Runtime.JAVA_21)
            .environment(Map.of("TABLE_NAME", dynamoDbTable.getTableName()))
            .build();
        dynamoDbTable.grantWriteData(funcMicronaut);

        // -------------------------------------- Spring --------------------------------------
        var funcSpring = Function.Builder.create(this, "order-function-spring")
            .functionName("order-function-spring")
            .code(Code.fromAsset("../order-function-spring/build/libs/order-function-spring-1.0-SNAPSHOT-aws.jar"))
            .memorySize(512)
            .handler("org.springframework.cloud.function.adapter.aws.FunctionInvoker::handleRequest")
            .runtime(Runtime.JAVA_21)
            .environment(Map.of("TABLE_NAME", dynamoDbTable.getTableName()))
            .build();
        dynamoDbTable.grantWriteData(funcSpring);

        // -------------------------------------- Python --------------------------------------
        var funcPython = Function.Builder.create(this, "order-function-python")
            .functionName("order-function-python")
            .code(Code.fromAsset("../order-function-python"))
            .memorySize(512)
            .handler("app.lambda_handler")
            .runtime(Runtime.PYTHON_3_13)
            .environment(Map.of("TABLE_NAME", dynamoDbTable.getTableName()))
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

}
