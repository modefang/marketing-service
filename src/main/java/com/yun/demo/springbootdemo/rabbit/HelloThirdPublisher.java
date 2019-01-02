package com.yun.demo.springbootdemo.rabbit;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class HelloThirdPublisher {
    private final String MESSAGE_TITLE = "#3 HelloThirdPublisher: ";

    @Resource
    private GlobalPublisher globalPublisher;

    public void sendBasic() {
        globalPublisher.sendBasic(this.MESSAGE_TITLE);
    }

    public void sendManyToMany() {
        globalPublisher.sendManyToMany(this.MESSAGE_TITLE);
    }

}
