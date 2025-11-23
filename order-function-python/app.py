import json
import uuid
from dataclasses import dataclass
from typing import Dict, Any


@dataclass
class Order:
    name: str


@dataclass
class SavedOrder:
    id: str
    name: str
    processed_by: str

    def to_dict(self) -> Dict[str, Any]:
        return {
            "id": self.id,
            "name": self.name,
            "processedBy": self.processed_by
        }


class OrderService:
    def save(self, order: Order) -> SavedOrder:
        order_id = str(uuid.uuid4())
        name = order.name.upper() if order.name else ""
        return SavedOrder(id=order_id, name=name, processed_by="Python")


def lambda_handler(event: Dict[str, Any], context) -> Dict[str, Any]:
    """
    AWS Lambda handler that processes Order requests and returns SavedOrder responses.
    """
    order_service = OrderService()

    # Parse the incoming order from the event
    order = Order(name=event.get("name", ""))

    # Process the order using the service
    saved_order = order_service.save(order)

    # Return the saved order as a dictionary
    return saved_order.to_dict()