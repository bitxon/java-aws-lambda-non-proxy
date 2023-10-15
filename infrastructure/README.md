# AWS CDK

## Install

```shell
npm install -g aws-cdk
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

## Use

* `cdk bootstrap`   [Bootstrap](https://docs.aws.amazon.com/cdk/v2/guide/bootstrapping.html)
* `cdk diff`        compare deployed stack with current state
* `cdk deploy`      deploy this stack to your default AWS account/region
* `cdk destroy`     destroy this stack in your default AWS account/region