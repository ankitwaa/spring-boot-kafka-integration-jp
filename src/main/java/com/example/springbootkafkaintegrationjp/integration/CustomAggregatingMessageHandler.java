package com.example.springbootkafkaintegrationjp.integration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.integration.aggregator.MessageGroupExpiredEvent;
import org.springframework.integration.store.MessageGroupStore;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Slf4j
public class CustomAggregatingMessageHandler implements ApplicationListener<MessageGroupExpiredEvent> {

    private MessageGroupStore store;

    @Autowired
    public void setStore(MessageGroupStore store) {
        this.store = store;
    }

    @Override
    public void onApplicationEvent(MessageGroupExpiredEvent event) {
        log.info("Received Group Expiry Event:{}", event);
        Collection<Message<?>> messages = store.getMessagesForGroup(event.getGroupId());
        log.info("Received Group Expiry Event Details:{}",messages);
        store.removeMessageGroup(event.getGroupId());
    }
}
