package com.example.springbootkafkaintegrationjp.integration;

import com.example.springbootkafkaintegrationjp.domain.FeedEvent;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

@MessagingGateway(errorChannel = "errorChannel")
public interface ChannelGateway {

    @Gateway(requestChannel = "validationChannel")
    void sendToValidationChannel(Message<FeedEvent> feedEventMessage);

    @Gateway(requestChannel = "feedChannel")
    void sendToFeedChannel(Message<FeedEvent> feedEventMessage);
}
