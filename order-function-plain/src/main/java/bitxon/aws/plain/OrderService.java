package bitxon.aws.plain;

import bitxon.aws.plain.model.Order;
import bitxon.aws.plain.model.SavedOrder;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

public class OrderService {

    public SavedOrder save(Order order) {
        var id = UUID.randomUUID();
        var name = StringUtils.upperCase(order.name());
        return new SavedOrder(id, name, "Plain Java");
    }
}