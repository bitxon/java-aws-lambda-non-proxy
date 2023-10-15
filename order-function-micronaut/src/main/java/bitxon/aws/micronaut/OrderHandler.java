package bitxon.aws.micronaut;

import bitxon.aws.micronaut.model.Order;
import bitxon.aws.micronaut.model.SavedOrder;
import io.micronaut.function.aws.MicronautRequestHandler;

import jakarta.inject.Inject;

public class OrderHandler extends MicronautRequestHandler<Order, SavedOrder> {
    @Inject
    OrderService orderService;

    @Override
    public SavedOrder execute(Order input) {
        return orderService.save(input);
    }
}
