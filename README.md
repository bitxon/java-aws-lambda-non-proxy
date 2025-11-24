# Java AWS Lambda

## Setup

1. Build all functions:
  ```shell
  ./gradlew clean build
  ```
2. Deploy via CDK:
  [Deploy instructions](infrastructure/README.md)

## Compare Cold Start

Java Runtime - Cold Start

| Tool      | Init (ms) | Duration (ms) | Billed (ms) | Memory (Mb) |
|-----------|-----------|---------------|-------------|-------------|
| Python    | 595       | 89            | 672         | 89          |
| Java      | 1710      | 2844          | 4556        | 165         |
| Quarkus   | 1341      | 5294          | 6635        | 186         |
| Micronaut | 3067      | 4095          | 7164        | 199         |
| Spring    | 4194      | 3303          | 7499        | 219         |
