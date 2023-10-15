package bitxon.aws.quarkus;

import bitxon.aws.quarkus.model.Order;
import bitxon.aws.quarkus.model.SavedOrder;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jakarta.inject.Inject;

public class OrderHandler implements RequestHandler<Order, SavedOrder> {

    @Inject
    protected OrderService orderService;

    @Override
    public SavedOrder handleRequest(Order input, Context context) {
        return orderService.save(input);
    }
}
