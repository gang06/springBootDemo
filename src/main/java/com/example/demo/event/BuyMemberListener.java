package com.example.demo.event;

import com.alibaba.fastjson.JSON;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class BuyMemberListener implements ApplicationListener<BuyMemberEvent> {

    @Override
    public void onApplicationEvent(BuyMemberEvent buyMemberEvent) {
        System.out.println(JSON.toJSONString(buyMemberEvent));
    }
}
