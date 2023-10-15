package bitxon.aws.micronaut;

import bitxon.aws.micronaut.model.Order;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class OrderLambdaMicronautTest {

    private static OrderHandler handler;

    @BeforeAll
    public static void setupServer() {
        handler = new OrderHandler();
    }

    @AfterAll
    public static void stopServer() {
        if (handler != null) {
            handler.getApplicationContext().close();
        }
    }

    @Test
    public void test() {
        // given
        var in = new Order("Birthday Gift");

        // when
        var response = handler.execute(in);

        // then
        assertEquals("BIRTHDAY GIFT", response.name());
        assertNotNull(response.id());
    }
}
