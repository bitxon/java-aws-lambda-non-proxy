import json
import uuid
import os
import boto3
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
    def __init__(self):
        self.dynamodb = boto3.resource('dynamodb')
        self.table_name = os.environ.get('TABLE_NAME')
        self.table = self.dynamodb.Table(self.table_name)

    def save(self, order: Order) -> SavedOrder:
        order_id = str(uuid.uuid4())
        name = order.name.upper() if order.name else ""
        processed_by = "Python"

        self.table.put_item(
            Item={
                'id': order_id,
                'name': name,
                'processedBy': processed_by
            }
        )

        return SavedOrder(id=order_id, name=name, processed_by=processed_by)


order_service = OrderService()

def lambda_handler(event: Dict[str, Any], context) -> Dict[str, Any]:
    order = Order(name=event.get("name", ""))
    saved_order = order_service.save(order)
    return saved_order.to_dict()