package com.yun.demo.springbootdemo.rabbit;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class helloSecondPublisher {
    private final String MESSAGE_TITLE = "#2 helloSecondPublisher: ";

    @Resource
    private GlobalPublisher globalPublisher;

    public void sendBasic() {
        globalPublisher.sendBasic(this.MESSAGE_TITLE);
    }

}
