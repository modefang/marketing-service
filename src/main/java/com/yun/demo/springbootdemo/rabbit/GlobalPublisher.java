package com.yun.demo.springbootdemo.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import javax.annotation.Resource;

public class GlobalPublisher {
    @Resource
    private static RabbitTemplate rabbitTemplate;

    @Resource
    private static SimpMessagingTemplate simpMessagingTemplate;

    public static void sendBasic(String title) {
        String message = "hello, this is first publisher!";
        rabbitTemplate.convertAndSend("hello", message);

        String print = title + message;
        simpMessagingTemplate.convertAndSend("/topic/sendHello", print);
        System.out.println(print);
    }
}
