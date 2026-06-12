package io.github.lilmosey.controller;


import io.agentscope.core.agent.Agent;
import io.agentscope.core.agent.Event;
import io.agentscope.core.message.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api")
public class DemoController {

    @Autowired
    private Agent agent;

    @GetMapping("/hello")
    public String hello() {
        return "hello agent";
    }

    @GetMapping("/chat")
    public String chat(String question){
        return agent.call(Msg.builder().textContent(question).build()).block().getTextContent();
    }

    @GetMapping(value = "/stream",produces = "text/plain;charset=UTF-8")
    public Flux<String> stream(String question){
        Flux<Event> stream = agent.stream(Msg.builder().textContent(question).build());
        return stream.map(event -> event.getMessage().getTextContent());
    }
}
