package com.example.springbootkafkaintegrationjp.audit.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.PollableChannel;

@Configuration
public class EventChannelDefinition {

    @Bean("eventChannel")
    public DirectChannel eventChannel(){
        return new DirectChannel();
    }

}
