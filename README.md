# agentscopedemo

Java 21 + Spring Boot demo project for future AgentScope-based intelligent agent and RAG experiments.

## Stack

- Java 21
- Spring Boot 3.4.13
- AgentScope Java 1.0.12
- Maven project layout

## Included Dependencies

- `spring-boot-starter-web`
- `io.agentscope:agentscope-spring-boot-starter`
- `io.agentscope:agentscope-extensions-rag-simple`

## Demo Endpoint

After starting the application, visit:

```text
GET http://localhost:8080/api/hello
```

Expected response:

```
hello agent
```

## Maven Compatibility

Spring Boot 3.4.x supports Maven 3.6.3 or later and requires Java 17 or later, so this project is intended to work with Maven 3.6.3 and Java 21.
