package bitxon.aws.micronaut;

import bitxon.aws.micronaut.model.Order;
import bitxon.aws.micronaut.model.SavedOrder;
import io.micronaut.context.annotation.Bean;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

@Bean
public class OrderService {

    public SavedOrder save(Order order) {
        var id = UUID.randomUUID();
        var name = StringUtils.upperCase(order.name());
        return new SavedOrder(id, name, "Micronaut");
    }
}