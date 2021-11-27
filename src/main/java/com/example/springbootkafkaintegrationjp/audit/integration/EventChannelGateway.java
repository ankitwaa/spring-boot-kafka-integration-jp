package com.example.springbootkafkaintegrationjp.audit.integration;

import com.example.springbootkafkaintegrationjp.domain.Event;
import com.example.springbootkafkaintegrationjp.domain.FeedEvent;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

@MessagingGateway(errorChannel = "errorChannel")
public interface EventChannelGateway {

    @Gateway(requestChannel = "validationChannel")
    void sendToEventChannel(Message<Event> feedEventMessage);

}
