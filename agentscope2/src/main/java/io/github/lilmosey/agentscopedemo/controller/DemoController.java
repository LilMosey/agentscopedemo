package io.github.lilmosey.agentscopedemo.controller;


import io.agentscope.core.ReActAgent;
import io.agentscope.core.agent.RuntimeContext;
import io.agentscope.core.event.TextBlockDeltaEvent;
import io.agentscope.core.message.ContentBlock;
import io.agentscope.core.message.Msg;
import io.agentscope.core.message.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DemoController {

    private static final String USER_ID = "Tom";

    @Autowired
    private ReActAgent reActAgent;

    @GetMapping("/hello")
    public String hello() {
        return "hello agent";
    }

    @GetMapping(value = "/stream",produces = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
    public Flux<String> stream(String message,String sessionId){
        return reActAgent.streamEvents(new UserMessage(message), RuntimeContext.builder().userId(USER_ID).sessionId(sessionId).build())
                .filter(agentEvent -> agentEvent instanceof TextBlockDeltaEvent)
                .map(agentEvent -> ((TextBlockDeltaEvent) agentEvent).getDelta());
    }

    @GetMapping(value = "/intercept",produces = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
    public void intercept(String sessionId){
        reActAgent.interrupt(RuntimeContext.builder().userId(USER_ID).sessionId(sessionId).build(),new UserMessage("用户已取消"));
        System.out.println("done");
    }

    @GetMapping(value = "/read",produces = MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
    public String read(String pathFile){
        Msg block = reActAgent.call(new UserMessage("帮我读取" + pathFile + "的内容")).block();
        List<ContentBlock> content = block.getContent();
        return reActAgent.call(new UserMessage("帮我读取" + pathFile +"的内容")).block().getTextContent();
    }


}
