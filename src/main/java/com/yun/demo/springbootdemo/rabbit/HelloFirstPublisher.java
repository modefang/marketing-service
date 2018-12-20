package com.yun.demo.springbootdemo.rabbit;

import com.yun.demo.springbootdemo.Util.JsonUtil;
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

    public void sendBasic() {
        String message = "hello, this is first publisher!";
        this.rabbitTemplate.convertAndSend("hello", message);
        System.out.println(this.MESSAGE_TITLE + message);
    }

    public void sendObject() {
        UserRabbitPojo user = new UserRabbitPojo();
        user.setId(UUID.randomUUID().toString());
        user.setName("modefang");
        this.rabbitTemplate.convertAndSend("object", user);
        System.out.println(this.MESSAGE_TITLE + JsonUtil.objectToJson(user));
    }

    public void sendTopic() {
        String message = "This message was sent by topic.message.";
        this.rabbitTemplate.convertAndSend("exchange", "topic.message", message);
        System.out.println(this.MESSAGE_TITLE + message);

        String messages = "This message was sent by topic.messages.";
        this.rabbitTemplate.convertAndSend("exchange", "topic.messages", messages);
        System.out.println(this.MESSAGE_TITLE + messages);
    }

    public void sendFanout() {
        String message = "This message was sent by fanoutExchange.";
        // routingKey可以随意设置，因为它不能对Fanout模式产生影响
        this.rabbitTemplate.convertAndSend("fanoutExchange","topic.message", message);
        System.out.println(this.MESSAGE_TITLE + message);
    }

}
