package bitxon.aws.spring;

import bitxon.aws.spring.model.Order;
import bitxon.aws.spring.model.SavedOrder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class OrderService {

    private final DynamoDbClient dynamoDbClient;
    private final String tableName;

    public OrderService(DynamoDbClient dynamoDbClient, @Value("${TABLE_NAME}") String tableName) {
        this.dynamoDbClient = dynamoDbClient;
        this.tableName = Objects.requireNonNull(tableName, "TABLE_NAME must be set");
    }

    public SavedOrder save(Order order) {
        var id = UUID.randomUUID();
        var name = StringUtils.upperCase(order.name());
        var processedBy = "Spring";

        var item = Map.of(
            "id", AttributeValue.builder().s(id.toString()).build(),
            "name", AttributeValue.builder().s(name).build(),
            "processedBy", AttributeValue.builder().s(processedBy).build()
        );

        var putItemRequest = PutItemRequest.builder()
            .tableName(tableName)
            .item(item)
            .build();

        dynamoDbClient.putItem(putItemRequest);

        return new SavedOrder(id, name, processedBy);
    }
}