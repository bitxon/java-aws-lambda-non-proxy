package bitxon.aws.spring;


import bitxon.aws.spring.model.Order;
import bitxon.aws.spring.model.SavedOrder;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class OrderHandler implements Function<Order, SavedOrder> {
    private final OrderService orderService;

    public OrderHandler(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public SavedOrder apply(Order input) {
        return orderService.save(input);
    }
}
