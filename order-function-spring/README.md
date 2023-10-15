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
File: `build/libs/order-function-spring-1.0-SNAPSHOT-aws.jar`\
Handler: `org.springframework.cloud.function.adapter.aws.FunctionInvoker::handleRequest`
Env: `N/A`
---
## Native
 TBD

## Guide
- AWS Lambda ([guide](https://docs.spring.io/spring-cloud-function/docs/current/reference/html/aws.html)): Write AWS Lambda functions
- AWS Lambda ([guide 2](https://cloud.spring.io/spring-cloud-function/reference/html/aws.html)): Write AWS Lambda functions
- Examples ([More about Spring AWS](https://github.com/spring-cloud/spring-cloud-function/tree/main/spring-cloud-function-samples))