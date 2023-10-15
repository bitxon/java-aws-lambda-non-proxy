package bitxon.aws.quarkus;

import bitxon.aws.quarkus.model.Order;
import bitxon.aws.quarkus.model.SavedOrder;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

@ApplicationScoped
public class OrderService {

    public SavedOrder save(Order order) {
        var id = UUID.randomUUID();
        var name = StringUtils.upperCase(order.name());
        return new SavedOrder(id, name, "Quarkus");
    }
}
