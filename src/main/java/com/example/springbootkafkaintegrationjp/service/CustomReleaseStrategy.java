package com.example.springbootkafkaintegrationjp.service;

import com.example.springbootkafkaintegrationjp.domain.FeedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.aggregator.ReleaseStrategy;
import org.springframework.integration.store.MessageGroup;
import org.springframework.messaging.Message;

import java.util.Iterator;

@Slf4j
public class CustomReleaseStrategy implements ReleaseStrategy {
    public static final long DEFAULT_TIMEOUT = 60000L;
    public static final int DEFAULT_THRESHOLD = 2147483647;
    private final int threshold;
    private final long timeout;

    public CustomReleaseStrategy() {
        this(2147483647, 60000L);
    }

    public CustomReleaseStrategy(int threshold, long timeout) {
        this.threshold = threshold;
        this.timeout = timeout;
    }

    public boolean canRelease(MessageGroup messages) {
        log.info("Checking whether it can be released or not");
        long elapsedTime = System.currentTimeMillis() - this.findEarliestTimestamp(messages);
        FeedEvent feedEvent = (FeedEvent) messages.getMessages().stream().findAny().get().getPayload();
        return messages.isComplete() || feedEvent.getCount() == messages.getMessages().size() || messages.getMessages().size() >= this.threshold || elapsedTime > this.timeout;
    }

    private long findEarliestTimestamp(MessageGroup messages) {
        long result = 9223372036854775807L;
        Iterator var4 = messages.getMessages().iterator();

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
