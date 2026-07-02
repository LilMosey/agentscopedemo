package io.github.lilmosey.agentscopedemo.demo;

import io.agentscope.core.ReActAgent;
import io.agentscope.core.agent.RuntimeContext;
import io.agentscope.core.message.UserMessage;
import io.agentscope.core.state.JsonFileAgentStateStore;

import java.nio.file.Paths;
import java.util.List;

public class AgentThreadDemo {
    public static void main(String[] args) {
        ReActAgent agent = ReActAgent.builder()
                .name("assistant")
                .sysPrompt("你是一个有帮助的助手。")
                .model("dashscope:qwen-plus")
                .stateStore(new JsonFileAgentStateStore(
                        Paths.get(System.getProperty("user.home"), ".agentscope/sessions")))
                .build();

// 在 HTTP handler 中——不同请求传入不同 RuntimeContext，各自隔离
        System.out.println(agent.call(List.of(new UserMessage("你好")),
                RuntimeContext.builder().userId("alice").sessionId("session-1").build()).block());

        System.out.println(agent.call(List.of(new UserMessage("Hi there")),
                RuntimeContext.builder().userId("bob").sessionId("session-2").build()).block());
    }
}
