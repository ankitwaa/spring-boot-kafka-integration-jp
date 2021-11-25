package com.example.springbootkafkaintegrationjp.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.PollableChannel;

@Configuration
public class ChannelDefinition {

    @Bean("validationChannel")
    public DirectChannel directChannel(){
        return new DirectChannel();
    }

    @Bean("feedChannel")
    public PollableChannel feedChannel(){
        return new QueueChannel(2000);
    }

    @Bean("batchChannel")
    public DirectChannel batchChannel(){
        return new DirectChannel();
    }
}
