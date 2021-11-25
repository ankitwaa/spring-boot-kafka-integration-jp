package com.example.springbootkafkaintegrationjp.service;

import com.example.springbootkafkaintegrationjp.domain.BatchFeedEvent;
import com.example.springbootkafkaintegrationjp.domain.FeedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BatchProcessing {

    @ServiceActivator(inputChannel = "batchChannel", outputChannel = "nullChannel")
    public Message<BatchFeedEvent> validate(Message<BatchFeedEvent> feedEventMessage){
        log.info("batch channel working:{}", feedEventMessage);
        return feedEventMessage;
    }
}
