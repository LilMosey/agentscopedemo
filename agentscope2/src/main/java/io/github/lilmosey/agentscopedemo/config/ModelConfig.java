package io.github.lilmosey.agentscopedemo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class ModelConfig {

    @Value("${deepseek.ai.key}")
    private String axAppKey;

    @Value("${deepseek.ai.baseUrl}")
    private String baseUrl;
}
