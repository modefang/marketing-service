package com.yun.demo.springbootdemo.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloFirstPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendBasic() {
        String message = "hello, this is first publisher!";
        this.rabbitTemplate.convertAndSend("hello", message);
        System.out.println("#1 HelloFirstPublisher: " + message);
    }

}
