package com.yun.demo.springbootdemo.rabbit;

import org.springframework.stereotype.Component;

@Component
public class helloSecondPublisher {
    private final String MESSAGE_TITLE = "#2 helloSecondPublisher: ";

    public void sendBasic() {
        GlobalPublisher.sendBasic(this.MESSAGE_TITLE);
    }

}
