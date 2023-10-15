# order-function-quarkus

<!-- TOC -->
* [order-function-quarkus](#order-function-quarkus)
  * [Java Runtime](#java-runtime)
    * [Build](#build)
    * [Handler](#handler)
  * [Native](#native)
  * [Guide](#guide)
<!-- TOC -->


---
## Java Runtime

### Build
```shell
gradle clean build
```

### Handler
File: `build/libs/order-function-plain-1.0-SNAPSHOT-all.jar`\
Handler: `bitxon.aws.plain.OrderHandler::handleRequest`
Env: `N/A`
---
## Native
 TBD

## Guide
- AWS Lambda ([guide](https://docs.aws.amazon.com/lambda/latest/dg/java-handler.html)): Write AWS Lambda functions
- Unit test ([junit guide](https://aws.amazon.com/blogs/opensource/testing-aws-lambda-functions-written-in-java/)): Write Junit Lambda tests