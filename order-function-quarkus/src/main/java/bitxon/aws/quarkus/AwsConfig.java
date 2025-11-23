package bitxon.aws.quarkus;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@ApplicationScoped
public class AwsConfig {

    @Produces
    @ApplicationScoped
    public DynamoDbClient dynamoDbClient() {
        return DynamoDbClient.builder()
            .httpClient(UrlConnectionHttpClient.builder().build())
            .build();
    }
}