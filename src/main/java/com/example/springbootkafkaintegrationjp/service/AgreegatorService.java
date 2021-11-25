package com.example.springbootkafkaintegrationjp.service;

import com.example.springbootkafkaintegrationjp.domain.BatchFeedEvent;
import com.example.springbootkafkaintegrationjp.domain.FeedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.Poller;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class AgreegatorService {

    @Aggregator(inputChannel = "feedChannel", outputChannel = "batchChannel", sendPartialResultsOnExpiry = "true",
            poller = @Poller(maxMessagesPerPoll = "5", receiveTimeout = "1000")
    )
    public BatchFeedEvent batchFeedEvent(List<Message<FeedEvent>> feedEventList){
        log.info("Inside batch feed handler {}");
        log.info("Inside batch feed handler {}, size:{}", feedEventList, feedEventList.size());
        BatchFeedEvent batchFeedEvent = new BatchFeedEvent();
        batchFeedEvent.setFeedEventList(feedEventList);
        return batchFeedEvent;
    }

}
