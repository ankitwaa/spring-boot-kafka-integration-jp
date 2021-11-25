package com.example.springbootkafkaintegrationjp.config.kafka;

import com.example.springbootkafkaintegrationjp.domain.FeedEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.aggregator.CorrelationStrategy;
import org.springframework.integration.aggregator.ReleaseStrategy;
import org.springframework.integration.aggregator.TimeoutCountSequenceSizeReleaseStrategy;
import org.springframework.messaging.Message;

@Configuration
public class IntegrationConfig{

    @Bean
    public ReleaseStrategy releaseStrategy(){
        return new TimeoutCountSequenceSizeReleaseStrategy();
    }

    @Bean
    public CorrelationStrategy correlationStrategy(){
        return new CorrelationStrategy() {
            @Override
            public Object getCorrelationKey(Message<?> message) {
                return ((FeedEvent)message.getPayload()).getOrderId();
            }
        };
    }

}
