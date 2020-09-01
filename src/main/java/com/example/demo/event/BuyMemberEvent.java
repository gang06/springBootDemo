package com.example.demo.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

@Data
public class BuyMemberEvent extends ApplicationEvent {

    private String message;

    public BuyMemberEvent(Object source, String message) {
        super(source);
        this.message = message;
    }

}
