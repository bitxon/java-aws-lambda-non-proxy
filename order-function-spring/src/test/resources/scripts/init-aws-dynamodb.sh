#!/bin/bash
echo "Creating DynamoDB tables..."

awslocal dynamodb create-table \
   --table-name Orders \
   --attribute-definitions AttributeName=id,AttributeType=S \
   --key-schema AttributeName=id,KeyType=HASH \
   --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5

echo "DynamoDB tables created."

