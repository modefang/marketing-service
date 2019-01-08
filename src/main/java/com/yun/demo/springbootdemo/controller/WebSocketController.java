package com.yun.demo.springbootdemo.controller;

import com.yun.demo.springbootdemo.constant.ResponseEnum;
import com.yun.demo.springbootdemo.pojo.ResponsePojo;
import com.yun.demo.springbootdemo.rabbit.HelloCallbackPublisher;
import com.yun.demo.springbootdemo.rabbit.HelloFirstPublisher;
import com.yun.demo.springbootdemo.rabbit.HelloThirdPublisher;
import com.yun.demo.springbootdemo.rabbit.helloSecondPublisher;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class WebSocketController {

    @Resource
    private HelloFirstPublisher helloFirstPublisher;

    @Resource
    private helloSecondPublisher helloSecondPublisher;

    @Resource
    private HelloThirdPublisher helloThirdPublisher;

    @Resource
    private HelloCallbackPublisher helloCallbackPublisher;

    @MessageExceptionHandler
    @SendTo("/topic/error")
    public ResponsePojo handleException(Throwable exception) {
        String message = exception.getMessage();
        System.out.println(message);
        return new ResponsePojo(ResponseEnum.ERROR, message);
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/reply")
    public ResponsePojo broadcast(String message) {
        if(message == null || "".equals(message)) {
            return new ResponsePojo(ResponseEnum.ERROR, -1);
        }
        return new ResponsePojo(ResponseEnum.SUCCESS, message);
    }

    @MessageMapping("/hello")
    public void hello() {
        helloFirstPublisher.sendBasic();
    }

    @MessageMapping("/object")
    public void object() {
        helloFirstPublisher.sendObject();
    }

    @MessageMapping("/manyToMany")
    public void manyToMany() {
        helloFirstPublisher.sendManyToMany();
        helloSecondPublisher.sendManyToMany();
        helloThirdPublisher.sendManyToMany();
    }

    @MessageMapping("/topic")
    public void topic() {
        helloFirstPublisher.sendTopic();
    }

    @MessageMapping("/fanout")
    public void fanout() {
        helloFirstPublisher.sendFanout();
    }

    @MessageMapping("/callback")
    public void callback() {
        helloCallbackPublisher.send("exchange", "topic.callback.message");
    }

}
