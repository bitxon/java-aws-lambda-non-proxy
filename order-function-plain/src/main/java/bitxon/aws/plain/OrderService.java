package bitxon.aws.plain;

import bitxon.aws.plain.model.Order;
import bitxon.aws.plain.model.SavedOrder;
import org.apache.commons.lang3.StringUtils;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.Map;
import java.util.UUID;

public class OrderService {

    private final DynamoDbClient dynamoDbClient;
    private final String tableName;

    public OrderService() {
        this.dynamoDbClient = DynamoDbClient.builder().build();
        this.tableName = System.getenv("TABLE_NAME");
    }

    public SavedOrder save(Order order) {
        var id = UUID.randomUUID();
        var name = StringUtils.upperCase(order.name());
        var processedBy = "Plain Java";

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