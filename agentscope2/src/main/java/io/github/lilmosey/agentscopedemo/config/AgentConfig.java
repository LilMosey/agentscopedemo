package io.github.lilmosey.agentscopedemo.config;

import io.agentscope.core.ReActAgent;
import io.agentscope.core.model.DashScopeChatModel;
import io.agentscope.core.model.OpenAIChatModel;
import io.agentscope.core.state.JsonFileAgentStateStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Paths;

@Configuration
public class AgentConfig {

    @Bean
    public ReActAgent reActAgent(){
        ReActAgent agent = ReActAgent.builder()
                .name("assistant")
                .sysPrompt("你是一个有帮助的助手。")
                .model(OpenAIChatModel.builder().apiKey())
                .stateStore(new JsonFileAgentStateStore(
                        Paths.get(System.getProperty("user.home"), ".agentscope/sessions")))
                .build();
    }
}
