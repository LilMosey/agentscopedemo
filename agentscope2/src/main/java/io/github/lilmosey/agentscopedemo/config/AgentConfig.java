package io.github.lilmosey.agentscopedemo.config;

import io.agentscope.core.ReActAgent;
import io.agentscope.core.formatter.openai.DeepSeekFormatter;
import io.agentscope.core.model.DashScopeChatModel;
import io.agentscope.core.model.OpenAIChatModel;
import io.agentscope.core.state.JsonFileAgentStateStore;
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
        return ReActAgent.builder()
                .name("assistant")
                .sysPrompt("你是一个有帮助的助手。")
                .model(OpenAIChatModel
                        .builder()
                        .apiKey(modelConfig.getAxAppKey())
                        .baseUrl(modelConfig.getBaseUrl())
                        .stream(true)
                        .formatter(new DeepSeekFormatter())
                        .build())
                .stateStore(new JsonFileAgentStateStore(
                        Paths.get(System.getProperty("user.home"), ".agentscope/sessions")))
                .build();
    }
}
