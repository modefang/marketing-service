package com.yun.demo.springbootdemo.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class helloSecondPublisher {
    private final String MESSAGE_TITLE = "#2 helloSecondPublisher: ";

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendBasic() {
        String message = "hello, this is second publisher!";
        this.rabbitTemplate.convertAndSend("hello", message);
        System.out.println(this.MESSAGE_TITLE + message);
    }

}
