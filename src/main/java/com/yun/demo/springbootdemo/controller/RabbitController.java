package com.yun.demo.springbootdemo.controller;

import com.yun.demo.springbootdemo.rabbit.HelloFirstPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbit")
public class RabbitController {

    @Autowired
    private HelloFirstPublisher helloFirstPublisher;

    @RequestMapping("/basic")
    public String basic() {
        helloFirstPublisher.sendBasic();
        return "success";
    }

//    @RequestMapping("/object")
//    public String object() {
//        helloFirstPublisher.sendObject();
//        return "success";
//    }
//
//    @RequestMapping("/oneToMany")
//    public String oneToMany() {
//        helloFirstPublisher.sendToMany();
//        return "success";
//    }
//
//    @RequestMapping("/manyToMany")
//    public String manyToMany() {
//        helloFirstPublisher.sendToMany();
//        return "success";
//    }
//
//    @RequestMapping("/topic")
//    public String topic() {
//        helloFirstPublisher.sendTopic();
//        return "success";
//    }
//
//    @RequestMapping("/fanout")
//    public String fanout() {
//        helloFirstPublisher.sendFanout();
//        return "success";
//    }

}
