package bitxon.aws.spring;

import bitxon.aws.spring.model.Order;
import bitxon.aws.spring.model.SavedOrder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {

    public SavedOrder save(Order order) {
        var id = UUID.randomUUID();
        var name = StringUtils.upperCase(order.name());
        return new SavedOrder(id, name, "Spring");
    }
}