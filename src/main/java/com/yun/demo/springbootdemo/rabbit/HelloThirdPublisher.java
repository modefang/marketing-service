package com.yun.demo.springbootdemo.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class HelloThirdPublisher {
    private final String MESSAGE_TITLE = "#3 HelloThirdPublisher: ";

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendBasic() {
        String message = "hello, this is third publisher!";
        this.rabbitTemplate.convertAndSend("hello", message);
        System.out.println(this.MESSAGE_TITLE + message);
    }

}
