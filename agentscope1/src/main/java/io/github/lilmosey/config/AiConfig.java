package io.github.lilmosey.config;

import io.agentscope.core.ReActAgent;
import io.agentscope.core.model.OllamaChatModel;
import io.agentscope.core.model.ollama.OllamaOptions;
import io.agentscope.core.model.ollama.ThinkOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class AiConfig {
    @Bean
    public OllamaChatModel ollamaChatModel(){
        return OllamaChatModel.builder()
                .defaultOptions(OllamaOptions.builder()
                        .thinkOption(ThinkOption.ThinkBoolean.ENABLED)
                        .build())
//                .baseUrl("http://localhost:11434")
                .modelName("qwen3:0.6b")
                .build();
    }

    @Bean
    public ReActAgent reActAgent(){
        return ReActAgent
                .builder()
                .name("hello agent")
                .model(ollamaChatModel())
                .build();
    }
}
