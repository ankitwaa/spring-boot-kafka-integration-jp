/*
package com.example.springbootkafkaintegrationjp.service;

import com.example.springbootkafkaintegrationjp.domain.BatchFeedEvent;
import com.example.springbootkafkaintegrationjp.domain.FeedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.CorrelationStrategy;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ReleaseStrategy;
import org.springframework.integration.store.MessageGroup;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Slf4j
@Component
public class AgreegatorService {

    private int threshold=2;
    private long timeout=1000;

    @Aggregator(inputChannel = "feedChannel", outputChannel = "batchChannel", sendPartialResultsOnExpiry = "true", sendTimeout = "1000",
            poller = @Poller(maxMessagesPerPoll = "5", receiveTimeout = "1000")
    )
    public BatchFeedEvent batchFeedEvent(List<Message<FeedEvent>> feedEventList){
        log.info("Inside batch feed handler {}, size:{}", feedEventList, feedEventList.size());
        BatchFeedEvent batchFeedEvent = new BatchFeedEvent();
        batchFeedEvent.setFeedEventList(feedEventList);
        return batchFeedEvent;
    }

    @CorrelationStrategy
    public String correlateBy(@Header("correlationId") String zip){
        return zip;
    }

   @ReleaseStrategy
    public boolean isReadytoRelease(List<Message<?>> messages) {
        log.info("called....");
        return canRelease(messages);
    }

    public boolean canRelease(List<Message<?>> messages) {
        long elapsedTime = System.currentTimeMillis() - this.findEarliestTimestamp(messages);
        return messages.size() >= this.threshold || elapsedTime > this.timeout;
    }

    private long findEarliestTimestamp(List<Message<?>> messages) {
        long result = 9223372036854775807L;
        Iterator var4 = messages.iterator();

        while(var4.hasNext()) {
            Message<?> message = (Message)var4.next();
            Long timestamp = message.getHeaders().getTimestamp();
            if (timestamp != null && timestamp < result) {
                result = timestamp;
            }
        }

        return result;
    }

}
*/
