package bitxon.aws.plain;

import bitxon.aws.plain.model.Order;
import bitxon.aws.plain.model.SavedOrder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class OrderHandler implements RequestHandler<Order, SavedOrder> {

    private final OrderService orderService = new OrderService();

    @Override
    public SavedOrder handleRequest(Order input, Context context) {
        return orderService.save(input);
    }
}
