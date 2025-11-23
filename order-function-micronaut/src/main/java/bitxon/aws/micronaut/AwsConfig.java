package bitxon.aws.micronaut;

import io.micronaut.context.annotation.Factory;
import jakarta.inject.Singleton;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Factory
public class AwsConfig {

    @Singleton
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder().build();
    }
}