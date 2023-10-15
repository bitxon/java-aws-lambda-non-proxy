# Java AWS Lambda

## Compare Cold Start

### Java Runtime - Cold Start
| Tool      | Init (ms) | Duration (ms) | Billed (ms) | Memory (Mb) | Jar (Mb) |
|-----------|-----------|---------------|-------------|-------------|----------|
| none      | 437       | 162           | 163         | 97          | 0.6      |
| Quarkus   | 1324      | 55            | 56          | 128         | 7.1      |
| Micronaut | 1893      | 1570          | 1571        | 138         | 12.4     |
| Spring    | 3036      | 695           | 696         | 158         | 17.7     |

### Custom (Native) Runtime - Cold Start
| Tool      | Init (ms) | Duration (ms) | Billed (ms) | Memory (Mb) | Jar (Mb) |
|-----------|-----------|---------------|-------------|-------------|----------|
| none      |           |               |             |             |          |
| Quarkus   | 216       | 228           | 446         | 46          | 12.9     |
| Micronaut |           |               |             |             |          |
| Spring    |           |               |             |             |          |