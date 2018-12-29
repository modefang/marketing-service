package com.yun.demo.springbootdemo.controller;

import com.yun.demo.springbootdemo.constant.ResponseEnum;
import com.yun.demo.springbootdemo.pojo.ResponseMessage;
import com.yun.demo.springbootdemo.rabbit.HelloCallbackPublisher;
import com.yun.demo.springbootdemo.rabbit.HelloFirstPublisher;
import com.yun.demo.springbootdemo.rabbit.HelloThirdPublisher;
import com.yun.demo.springbootdemo.rabbit.helloSecondPublisher;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class WebSocketController {

    @Resource
    private SimpMessagingTemplate simpMessagingTemplate;

    @Resource
    private HelloFirstPublisher helloFirstPublisher;

    @Resource
    private helloSecondPublisher helloSecondPublisher;

    @Resource
    private HelloThirdPublisher helloThirdPublisher;

    @Resource
    private HelloCallbackPublisher helloCallbackPublisher;

    @MessageMapping("/sendMessage")
    @SendTo("/topic/reply")
    public ResponseMessage broadcast(String message) {
        if(message == null || "".equals(message)) {
            return new ResponseMessage(ResponseEnum.ERROR, -1);
        }
        return new ResponseMessage(ResponseEnum.SUCCESS, message);
    }

    @MessageMapping("/hello")
    public void hello() {
        helloFirstPublisher.sendBasic();
    }

    @MessageExceptionHandler
    public ResponseMessage handleException(Throwable exception) {
        simpMessagingTemplate.convertAndSend("/websocket/error", exception.getMessage());
        return new ResponseMessage(ResponseEnum.ERROR, -1);
    }

}
