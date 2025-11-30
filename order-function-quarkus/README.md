# order-function-quarkus

<!-- TOC -->
* [order-function-quarkus](#order-function-quarkus)
  * [Java Runtime](#java-runtime)
    * [Build](#build)
    * [Handler](#handler)
  * [Native](#native)
    * [Build](#build-1)
    * [Handler](#handler-1)
  * [Guide](#guide)
<!-- TOC -->


---
## Java Runtime

### Build
```shell
gradle clean build
```

### Handler
File: `build/function.zip`\
Handler: `io.quarkus.amazon.lambda.runtime.QuarkusStreamHandler::handleRequest`
Env: `N/A`
---
## Native
### Build

```shell script
gradle clean build -Dquarkus.package.jar.enabled=false -Dquarkus.native.enabled=true -Dquarkus.native.container-build=true
```

### Handler
File: `build/function.zip`\
Handler: `N/A`
Env: `DISABLE_SIGNAL_HANDLERS: true`
Note: If you build on Apple M1 you should use `arm64` architecture for `function.zip` file

## Guide
- AWS Lambda ([guide](https://quarkus.io/guides/aws-lambda)): Write AWS Lambda functions
- Quarkus Gradle ([More about Gradle](https://quarkus.io/guides/gradle-tooling)): More about building native executables
- Snap Start ([Quarkus SnapStart vs Native](https://quarkus.io/blog/quarkus-support-for-aws-lambda-snapstart/)): Compare SnapStart and Native