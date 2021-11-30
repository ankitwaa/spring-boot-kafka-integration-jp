package com.example.springbootkafkaintegrationjp.service;

import com.example.springbootkafkaintegrationjp.audit.repo.entity.FileMetricEvent;
import com.example.springbootkafkaintegrationjp.domain.FeedEvent;
import com.example.springbootkafkaintegrationjp.integration.ChannelGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.integration.aggregator.MessageGroupExpiredEvent;
import org.springframework.integration.mongodb.store.MongoDbMessageStore;
import org.springframework.integration.store.MessageGroupStore;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CustomAggregatingMessageHandler implements ApplicationListener<MessageGroupExpiredEvent> {

    private MongoDbMessageStore store;
    private ChannelGateway channelGateway;

    @Autowired
    public void setStore(MongoDbMessageStore store) {
        this.store = store;
    }

    @Autowired
    public void setChannelGateway(ChannelGateway channelGateway) {
        this.channelGateway = channelGateway;
    }

    @Override
    public void onApplicationEvent(MessageGroupExpiredEvent event) {
        log.info("Received Group Expiry Event:{}", event);
        Collection<Message<?>> messages = store.getMessagesForGroup(event.getGroupId());
        createFileMetricEvent(messages);
        log.info("Received Group Expiry Event Details:{}",messages);
        store.removeMessageGroup(event.getGroupId());
    }

    private void createFileMetricEvent(Collection<Message<?>> messages) {
        FileMetricEvent fileMetricEvent = new FileMetricEvent();
        if(messages != null && messages.size() > 0){
            Optional<Message<?>> feedEvent = messages.stream().findFirst();
            FeedEvent feedEvent1 = (FeedEvent) feedEvent.get().getPayload();
            fileMetricEvent.setFileName(feedEvent1.getOrderId());
            fileMetricEvent.setExpectedCount(feedEvent1.getCount());
            fileMetricEvent.setReceivedCount(messages.size());
            fileMetricEvent.setMessageIds(messages.stream().map(message -> ((FeedEvent)message.getPayload()).getValue()).collect(Collectors.toList()));
            fileMetricEvent.setStatus("FAILED");
            channelGateway.sendToFileMetricsChannel(MessageBuilder.withPayload(fileMetricEvent).build());
        }
    }
}
