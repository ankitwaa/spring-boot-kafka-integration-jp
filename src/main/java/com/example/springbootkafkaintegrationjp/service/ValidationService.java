package com.example.springbootkafkaintegrationjp.service;

import com.example.springbootkafkaintegrationjp.domain.FeedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.IntegrationMessageHeaderAccessor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ValidationService {

    @ServiceActivator(inputChannel = "validationChannel", outputChannel = "feedChannel")
    public Message<FeedEvent>  validate(Message<FeedEvent> feedEventMessage){
        log.info("validation channel working:{}", feedEventMessage);
        return feedEventMessage;
    }
}
