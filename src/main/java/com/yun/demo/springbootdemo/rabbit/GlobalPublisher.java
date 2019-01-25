package com.yun.demo.springbootdemo.rabbit;

import com.yun.demo.springbootdemo.util.JsonUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import rabbit.pojo.UserRabbitPojo;

import javax.annotation.Resource;
import java.util.UUID;

@Component
public class GlobalPublisher {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    public void sendBasic(String title) {
        String message = "hello, this is first publisher!";
        rabbitTemplate.convertAndSend("hello", message);

        String print = title + message;
        simpMessagingTemplate.convertAndSend("/topic/sendHello", print);
        System.out.println(print);
    }

    public void sendObject(String title) {
        UserRabbitPojo user = new UserRabbitPojo();
        user.setId(UUID.randomUUID().toString());
        user.setName("modefang");
        this.rabbitTemplate.convertAndSend("object", user);

        String print = title + JsonUtils.objectToJson(user);
        this.simpMessagingTemplate.convertAndSend("/topic/sendObject", print);
        System.out.println(print);
    }

    public void sendManyToMany(String title) {
        String message = "This message was sent by queue.manyToMany.";
        rabbitTemplate.convertAndSend("manyToMany", message);

        String print = title + message;
        simpMessagingTemplate.convertAndSend("/topic/sendManyToMany", print);
        System.out.println(print);
    }

    public void sendTopic(String title) {
        String message = "This message was sent by topic.message.";
        this.rabbitTemplate.convertAndSend("exchange", "topic.message", message);
        String print1 = title + message;
        this.simpMessagingTemplate.convertAndSend("/topic/sendTopic", print1);
        System.out.println(print1);

        String messages = "This message was sent by topic.messages.";
        this.rabbitTemplate.convertAndSend("exchange", "topic.messages", messages);
        String print2 = title + messages;
        this.simpMessagingTemplate.convertAndSend("/topic/sendTopic", print2);
        System.out.println(print2);
    }

    public void sendFanout(String title) {
        String message = "This message was sent by fanoutExchange.";
        // routingKey可以随意设置，因为它不能对Fanout模式产生影响
        this.rabbitTemplate.convertAndSend("fanoutExchange","topic.message", message);

        String print = title + message;
        this.simpMessagingTemplate.convertAndSend("/topic/sendFanout", print);
        System.out.println(print);
    }

}
