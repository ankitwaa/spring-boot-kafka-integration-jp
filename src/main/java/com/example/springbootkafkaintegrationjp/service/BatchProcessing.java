package com.example.springbootkafkaintegrationjp.service;

import com.example.springbootkafkaintegrationjp.domain.BatchFeedEvent;
import com.example.springbootkafkaintegrationjp.domain.FeedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class BatchProcessing {

    @ServiceActivator(inputChannel = "batchChannel", outputChannel = "tempFileCreationChannel")
    public BatchFeedEvent validate(Message<List<FeedEvent>> feedEventMessage){
        log.info("batch channel working:{}", feedEventMessage);
        BatchFeedEvent batchFeedEvent = new BatchFeedEvent();
        batchFeedEvent.setFeedEventList(feedEventMessage);
        return batchFeedEvent;
    }
}
