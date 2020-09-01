package com.example.demo.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class EventPublish {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public void publish(String message){
        BuyMemberEvent buyMemberEvent = new BuyMemberEvent(this, message);
        buyMemberEvent.setMessage(message);
        eventPublisher.publishEvent(buyMemberEvent);
    }


}
