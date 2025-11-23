package bitxon.aws.plain;

import bitxon.aws.plain.model.Order;
import com.amazonaws.services.lambda.runtime.tests.annotations.Event;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled // TODO implement with testcontainers-localstack
class OrderLambdaPlainTest {

    OrderHandler handler = new OrderHandler();

    @ParameterizedTest
    @Event(value = "test-event.json", type = Order.class)
    public void test(Order in) {
        // when
        var response = handler.handleRequest(in, null);

        // then
        assertEquals("BIRTHDAY GIFT", response.name());
        assertNotNull(response.id());
    }

}