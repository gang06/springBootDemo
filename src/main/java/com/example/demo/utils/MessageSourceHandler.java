package com.example.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Locale;

@Component
@Slf4j
public class MessageSourceHandler {
    @Resource
    private MessageSource messageSource;

    public String getMessage(String messageKey) {
        try {
            if (true) {
                return messageSource.getMessage(messageKey, null, Locale.US);
            }
            return messageSource.getMessage(messageKey, null, Locale.SIMPLIFIED_CHINESE);
        } catch (NoSuchMessageException e) {
            log.error("读取国际化资源文件时出现异常:", e);
            return "";
        }
    }
}
