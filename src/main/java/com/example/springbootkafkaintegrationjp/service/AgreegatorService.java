package com.example.springbootkafkaintegrationjp.service;

import com.example.springbootkafkaintegrationjp.domain.BatchFeedEvent;
import com.example.springbootkafkaintegrationjp.domain.FeedEvent;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.Poller;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AgreegatorService {

    @Aggregator(inputChannel = "feedChannel", outputChannel = "batchChannel", sendPartialResultsOnExpiry = "true",
            poller = @Poller(maxMessagesPerPoll = "5", receiveTimeout = "1000")
    )
    public BatchFeedEvent batchFeedEvent(List<FeedEvent> feedEventList){
        BatchFeedEvent batchFeedEvent = new BatchFeedEvent();
        batchFeedEvent.setFeedEventList(feedEventList);
        return batchFeedEvent;
    }
}
