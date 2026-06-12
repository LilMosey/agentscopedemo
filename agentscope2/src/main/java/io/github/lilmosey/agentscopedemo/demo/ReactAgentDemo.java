package io.github.lilmosey.agentscopedemo.demo;

import io.agentscope.core.ReActAgent;
import io.agentscope.core.message.Msg;
import io.agentscope.core.message.UserMessage;
import io.agentscope.core.model.OllamaChatModel;
import io.agentscope.core.tool.Toolkit;
import io.agentscope.core.tool.file.ReadFileTool;
import io.agentscope.core.tool.file.WriteFileTool;
import io.github.lilmosey.agentscopedemo.tool.WeatherTool;

public class ReactAgentDemo {
    public static void main(String[] args) {
        Toolkit toolkit = new Toolkit();
        toolkit.registerTool(new WeatherTool());
        toolkit.registerTool(new WriteFileTool());
        toolkit.registerTool(new ReadFileTool());
        ReActAgent agent =
                ReActAgent.builder()
                        .name("my_agent")
                        .sysPrompt("你是一个有帮助的助手。")
                        // 由 ModelRegistry 解析；自动读取 DASHSCOPE_API_KEY
                        // 切换其他厂商时改成 "openai:gpt-5.5" / "anthropic:claude-sonnet-4-5"
                        // / "gemini:gemini-2.0-flash" / "ollama:llama3" 即可。
                        .model("dashscope:qwen-plus")
                        .toolkit(toolkit)
                        .build();

//        Msg block = agent.call(new UserMessage("你好,今天洛杉矶的天气是")).block();
//        System.out.println(block.getTextContent());
        Msg block = agent.call(new UserMessage("帮我读取 /Users/tangjie/Downloads/sdd导出.md的内容")).block();
        System.out.println(block.getTextContent());
    }
}
