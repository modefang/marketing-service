package com.yun.demo.springbootdemo.rabbit;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class HelloFirstPublisher {
    private final String MESSAGE_TITLE = "#1 HelloFirstPublisher: ";

    @Resource
    private GlobalPublisher globalPublisher;

    public void sendBasic() {
        globalPublisher.sendBasic(this.MESSAGE_TITLE);
    }

    public void sendObject() {
        globalPublisher.sendObject(this.MESSAGE_TITLE);
    }

    public void sendTopic() {
        globalPublisher.sendTopic(this.MESSAGE_TITLE);
    }

    public void sendFanout() {
        globalPublisher.sendFanout(this.MESSAGE_TITLE);
    }

}
