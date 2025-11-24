# AWS CDK

## Install Tools

```shell
# Base AWS tools
brew install awscli
npm install -g aws-cdk

# LocalStack tools
brew install localstack/tap/localstack-cli
brew install awscli-local
npm install -g aws-cdk-local
```

## LocalStack Setup
<details>
  <summary>See details: to run on local machine</summary>

  ### Start LocalStack
  ```shell
  localstack start
  ```

  ### Use CDK
  * `cdklocal bootstrap`   [Bootstrap](https://docs.aws.amazon.com/cdk/v2/guide/bootstrapping.html)
  * `cdklocal diff`        compare deployed stack with current state
  * `cdklocal deploy`      deploy this stack to your localstack AWS account/region
  * `cdklocal destroy`     destroy this stack in your localstack AWS account/region

    
  ### Verify Lambda functions
  ```shell
  awslocal lambda list-functions --query "Functions[].FunctionName" --region us-east-1
  ```
  ```shell
  awslocal lambda invoke --function-name order-function-plain \
    --payload '{ "name": "Birthday Gift" }' \
    --region us-east-1 \
    --cli-binary-format raw-in-base64-out \
    output.json && cat output.json | jq .
  ```
  ```shell
  awslocal lambda invoke --function-name order-function-python \
    --payload '{ "name": "Birthday Gift" }' \
    --region us-east-1 \
    --cli-binary-format raw-in-base64-out \
    output.json && cat output.json | jq .
  ```
  ```shell
  awslocal dynamodb get-item \
    --table-name Orders \
    --key '{"id":{"S":"733197b5-2c5c-42a1-8c97-47d3c00c47f5"}}' \
    --region us-east-1 \
    --output json
  ```


</details>

## Prod Setup
<details>
  <summary>See details: to run on real AWS account</summary>


  ### SetUp
  Configure AWS CLI credentials
  ```shell
  aws configure
  # aws configure sso # modern way
  ```
  Configure accountId and region for CDK
  ```shell
  CDK_DEFAULT_ACCOUNT=...
  CDK_DEFAULT_REGION=...
  ```

  ### Use CDK
  * `cdk bootstrap`   [Bootstrap](https://docs.aws.amazon.com/cdk/v2/guide/bootstrapping.html)
  * `cdk diff`        compare deployed stack with current state
  * `cdk deploy`      deploy this stack to your default AWS account/region
  * `cdk destroy`     destroy this stack in your default AWS account/region


### Verify Lambda functions
  ```shell
  aws lambda list-functions --query "Functions[].FunctionName"
  ```
  ```shell
  aws lambda invoke --function-name order-function-plain \
    --payload '{ "name": "Birthday Gift" }' \
    --cli-binary-format raw-in-base64-out \
    output.json && cat output.json | jq .
  ```
  ```shell
  awslambda invoke --function-name order-function-python \
    --payload '{ "name": "Birthday Gift" }' \
    --cli-binary-format raw-in-base64-out \
    output.json && cat output.json | jq .
  ```
  ```shell
  aws dynamodb get-item \
    --table-name Orders \
    --key '{"id":{"S":"b6c0ecb5-29f3-471a-a9b9-e65e3e930835"}}' \
    --output json
  ```

</details>
