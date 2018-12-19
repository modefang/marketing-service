package com.yun.demo.springbootdemo.rabbit;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelloFirstPublisher {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendBasic() {
        String message = "hello, this is first publisher!";
        System.out.println("HelloFirstPublisher : " + message);
        this.rabbitTemplate.convertAndSend("helloQueue", message);
    }

}
