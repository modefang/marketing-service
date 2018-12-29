package com.yun.demo.springbootdemo.rabbit;

import com.yun.demo.springbootdemo.Util.JsonUtil;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import rabbit.pojo.UserRabbitPojo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

@Component
public class HelloFirstPublisher {
    private final String MESSAGE_TITLE = "#1 HelloFirstPublisher: ";

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    public void sendBasic() {
        GlobalPublisher.sendBasic(this.MESSAGE_TITLE);
    }

    public void sendObject() {
        UserRabbitPojo user = new UserRabbitPojo();
        user.setId(UUID.randomUUID().toString());
        user.setName("modefang");
        this.rabbitTemplate.convertAndSend("object", user);
        this.simpMessagingTemplate.convertAndSend("/topic/sendObject", user);
        System.out.println(this.MESSAGE_TITLE + JsonUtil.objectToJson(user));
    }

    public void sendTopic() {
        String message = "This message was sent by topic.message.";
        this.rabbitTemplate.convertAndSend("exchange", "topic.message", message);
        this.simpMessagingTemplate.convertAndSend("/topic/sendTopic", message);
        System.out.println(this.MESSAGE_TITLE + message);

        String messages = "This message was sent by topic.messages.";
        this.rabbitTemplate.convertAndSend("exchange", "topic.messages", messages);
        this.simpMessagingTemplate.convertAndSend("/topic/sendTopic", messages);
        System.out.println(this.MESSAGE_TITLE + messages);
    }

    public void sendFanout() {
        String message = "This message was sent by fanoutExchange.";
        // routingKey可以随意设置，因为它不能对Fanout模式产生影响
        this.rabbitTemplate.convertAndSend("fanoutExchange","topic.message", message);
        this.simpMessagingTemplate.convertAndSend("/topic/sendFanout", message);
        System.out.println(this.MESSAGE_TITLE + message);
    }

}
