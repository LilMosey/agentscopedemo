package io.github.lilmosey.agentscopedemo.config;

import io.agentscope.core.ReActAgent;
import io.agentscope.core.formatter.openai.DeepSeekFormatter;
import io.agentscope.core.model.DashScopeChatModel;
import io.agentscope.core.model.OpenAIChatModel;
import io.agentscope.core.state.JsonFileAgentStateStore;
import io.agentscope.core.tool.Toolkit;
import io.agentscope.core.tool.file.ReadFileTool;
import io.agentscope.core.tool.file.WriteFileTool;
import io.github.lilmosey.agentscopedemo.tool.WeatherTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Paths;

@Configuration
public class AgentConfig {

    @Autowired
    private ModelConfig modelConfig;

    @Bean
    public ReActAgent reActAgent(){
        Toolkit toolkit = new Toolkit();
        toolkit.registerTool(new WeatherTool());
        toolkit.registerTool(new WriteFileTool());
        toolkit.registerTool(new ReadFileTool());
        return ReActAgent.builder()
                .name("assistant")
                .sysPrompt("你是一个有帮助的助手。")
                .model(OpenAIChatModel
                        .builder()
                        .apiKey(modelConfig.getAxAppKey())
                        .baseUrl(modelConfig.getBaseUrl())
                        .modelName(modelConfig.getModelName())
                        .stream(true)
                        .formatter(new DeepSeekFormatter())
                        .build())
                .stateStore(new JsonFileAgentStateStore(
                        Paths.get(System.getProperty("user.home"), ".agentscope/sessions")))
                .toolkit(toolkit)
                .build();
    }
}
