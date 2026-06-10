package io.github.lilmosey.agentscopedemo.test;
import io.agentscope.core.agent.RuntimeContext;
import io.agentscope.core.message.ContentBlock;
import io.agentscope.core.message.Msg;
import io.agentscope.core.message.TextBlock;
import io.agentscope.core.message.UserMessage;
import io.agentscope.harness.agent.HarnessAgent;
import io.agentscope.harness.agent.memory.compaction.CompactionConfig;
import java.nio.file.Paths;
import java.util.List;

public class FirstAgent {
    public static void main(String[] args) {
        HarnessAgent agent = HarnessAgent.builder()
                .name("note-taker")
                .sysPrompt("你是一个帮助用户做笔记的助手。")
                // 字符串形式由 ModelRegistry 解析 —— 自动读取 DASHSCOPE_API_KEY；
                // 切换其他厂商时改用 "openai:gpt-5.5"、"anthropic:claude-sonnet-4-5"、
                // "gemini:gemini-2.0-flash" 或 "ollama:llama3"。
                .model("dashscope:qwen-plus")
                .workspace(Paths.get(".agentscope/workspace"))
                .compaction(CompactionConfig.builder()
                        .triggerMessages(30)
                        .keepMessages(10)
                        .build())
                .build();

        RuntimeContext ctx = RuntimeContext.builder()
                .sessionId("demo-session-2")
                .userId("alice-2")
                .build();

        // 第一轮：自我介绍 + 当天的事
        Msg block = agent.call(new UserMessage("我叫唐杰，今天准备一个关于 ReAct 的技术分享。"), ctx).block();
        List<TextBlock> contentBlocks = block.getContentBlocks(TextBlock.class);
        for (TextBlock contentBlock : contentBlocks) {
            System.out.println(contentBlock);
        }

        System.out.println("--------------");

        // 第二轮：同 sessionId，自动恢复上一轮状态后回答
        Msg block1 = agent.call(new UserMessage("我叫什么？我今天要干什么？"), ctx).block();
        List<TextBlock> contentBlocks1 = block1.getContentBlocks(TextBlock.class);
        for (TextBlock contentBlock : contentBlocks1) {
            System.out.println(contentBlock);
        }
//        agent.call(new UserMessage("请把我的名字和今天要做的事写入工作区 你自己创建文件及文件夹。"), ctx).block();
    }
}
