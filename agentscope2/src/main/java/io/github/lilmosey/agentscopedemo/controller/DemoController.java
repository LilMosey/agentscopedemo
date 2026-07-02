package io.github.lilmosey.agentscopedemo.controller;


import io.agentscope.core.ReActAgent;
import io.agentscope.core.agent.RuntimeContext;
import io.agentscope.core.event.TextBlockDeltaEvent;
import io.agentscope.core.message.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

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


}
