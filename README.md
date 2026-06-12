# agentscopedemo

Java 21 + Spring Boot 的 AgentScope 示例工程，用来对比和试验 AgentScope Java 的两个版本：

- `agentscope1`: AgentScope Java `1.0.12`
- `agentscope2`: AgentScope Java `2.0.0-RC3`

根目录是 Maven 父工程，两个版本分别放在独立 module 中，便于并行保留旧版写法和新版写法。

## 项目结构

```text
agentscopedemo
├── pom.xml
├── agentscope1
│   ├── pom.xml
│   └── src/main
└── agentscope2
    ├── pom.xml
    └── src/main
```

## 技术栈

- Java 21
- Spring Boot 3.5.8
- Maven 多模块工程
- AgentScope Java 1.x / 2.x

## 版本说明

### agentscope1

`agentscope1` 使用 AgentScope Java `1.0.12`，主要依赖：

- `io.agentscope:agentscope`
- `io.agentscope:agentscope-spring-boot-starter`
- `org.springframework.boot:spring-boot-starter-web`

该模块配置了一个基于 Ollama 的 `ReActAgent`：

- 模型名：`qwen3:0.6b`
- 默认 Ollama 地址：代码中未显式指定，使用 AgentScope/Ollama 默认配置
- 服务端口：`8081`

接口：

```text
GET http://localhost:8081/api/hello
GET http://localhost:8081/api/chat?question=你好
GET http://localhost:8081/api/stream?question=你好
```

### agentscope2

`agentscope2` 使用 AgentScope Java `2.0.0-RC3`，主要依赖：

- `io.agentscope:agentscope-harness`
- `io.agentscope:agentscope-spring-boot-starter`
- `io.agentscope:agentscope-extensions-rag-simple`
- `org.springframework.boot:spring-boot-starter-web`

该模块目前包含两类示例：

- Spring Boot Web demo：`/api/hello`
- HarnessAgent 命令行 demo：`FirstAgent`

服务端口：`8082`

Web 接口：

```text
GET http://localhost:8082/api/hello
```

命令行示例位于：

```text
agentscope2/src/main/java/io/github/lilmosey/agentscopedemo/test/FirstAgent.java
```

`FirstAgent` 使用 `dashscope:qwen-plus`，运行前需要配置 `DASHSCOPE_API_KEY`。

## 运行方式

在项目根目录运行指定模块。

启动 AgentScope 1.x 示例：

```bash
mvn -pl agentscope1 spring-boot:run
```

启动 AgentScope 2.x 示例：

```bash
mvn -pl agentscope2 spring-boot:run
```

如果需要先编译整个工程：

```bash
mvn clean package
```

## 注意事项

- 两个模块使用不同端口，可以同时启动。
- `agentscope1` 的对话接口依赖本地 Ollama 和 `qwen3:0.6b` 模型。
- `agentscope2` 的 `FirstAgent` 依赖 DashScope API Key。
- 根 POM 中统一维护 AgentScope 版本：

```xml
<agentscope2.version>2.0.0-RC3</agentscope2.version>
<agentscope1.version>1.0.12</agentscope1.version>
```
