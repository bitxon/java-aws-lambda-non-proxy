# AWS CDK

## Install

```shell
# Base AWS
brew install awscli
npm install -g aws-cdk

# LocalStack
brew install localstack/tap/localstack-cli
brew install awscli-local
npm install -g aws-cdk aws-cdk-local
```

## SetUp
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

## LocalStack Setup
<details>
  <summary>See details</summary>

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
    --key '{"id":{"S":"a7cd698e-85cb-4321-a7e5-dd2d53b50510"}}' \
    --region us-east-1 \
    --output json
  ```


</details>

## Prod Setup
<details>
  <summary>See details</summary>

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

</details>
