package com.yun.demo.springbootdemo.rabbit;

import org.springframework.stereotype.Component;

@Component
public class HelloThirdPublisher {
    private final String MESSAGE_TITLE = "#3 HelloThirdPublisher: ";

    public void sendBasic() {
        GlobalPublisher.sendBasic(this.MESSAGE_TITLE);
    }

}
