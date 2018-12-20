package com.yun.demo.springbootdemo.controller;

import com.yun.demo.springbootdemo.rabbit.HelloCallbackPublisher;
import com.yun.demo.springbootdemo.rabbit.HelloFirstPublisher;
import com.yun.demo.springbootdemo.rabbit.HelloThirdPublisher;
import com.yun.demo.springbootdemo.rabbit.helloSecondPublisher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/rabbit")
public class RabbitController {

    @Resource
    private HelloFirstPublisher helloFirstPublisher;

    @Resource
    private helloSecondPublisher helloSecondPublisher;

    @Resource
    private HelloThirdPublisher helloThirdPublisher;

    @Resource
    private HelloCallbackPublisher helloCallbackPublisher;

    @RequestMapping("/basic")
    public String basic(Integer num) {
        num = num == null || num < 1 ? 1 : num;
        for (int i = 0; i < num; i++) {
            helloFirstPublisher.sendBasic();
        }
        return "success";
    }

    @RequestMapping("/object")
    public String object(Integer num) {
        num = num == null || num < 1 ? 1 : num;
        for (int i = 0; i < num; i++) {
            helloFirstPublisher.sendObject();
        }
        return "success";
    }

    @RequestMapping("/manyToMany")
    public String manyToMany(Integer num) {
        num = num == null || num < 1 ? 1 : num;
        for (int i = 0; i < num; i++) {
            helloFirstPublisher.sendBasic();
            helloSecondPublisher.sendBasic();
            helloThirdPublisher.sendBasic();
        }
        return "success";
    }

    @RequestMapping("/topic")
    public String topic(Integer num) {
        num = num == null || num < 1 ? 1 : num;
        for (int i = 0; i < num; i++) {
            helloFirstPublisher.sendTopic();
        }
        return "success";
    }

    @RequestMapping("/fanout")
    public String fanout(Integer num) {
        num = num == null || num < 1 ? 1 : num;
        for (int i = 0; i < num; i++) {
            helloFirstPublisher.sendFanout();
        }
        return "success";
    }

    @RequestMapping("/callback")
    public String callback(Integer num) {
        num = num == null || num < 1 ? 1 : num;
        for (int i = 0; i < num; i++) {
            helloCallbackPublisher.send("exchange", "topic.messages");
        }
        return "success";
    }

}
