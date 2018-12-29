package com.yun.demo.springbootdemo.rabbit;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

@Component
public class HelloCallbackPublisher implements RabbitTemplate.ConfirmCallback {
    private final String MESSAGE_TITLE = "HelloCallbackPublisher: ";

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public HelloCallbackPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setConfirmCallback(this);
    }

    public void send(String exchange, String routingKey) {
        String id = UUID.randomUUID().toString();
        String message = "[id: " + id + "] This message has callback.";
        CorrelationData correlationData = new CorrelationData(id);
        this.rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);
        System.out.println(this.MESSAGE_TITLE + message);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if(ack) {
            System.out.println("[id: " + correlationData.getId() + "] callback success.");
        } else {
            System.out.println("[id: " + correlationData.getId() + "] callback error: " + cause + ".");
        }
    }

}
