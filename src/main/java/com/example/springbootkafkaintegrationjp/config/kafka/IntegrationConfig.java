package com.example.springbootkafkaintegrationjp.config.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.aggregator.DefaultAggregatingMessageGroupProcessor;
import org.springframework.integration.aggregator.ReleaseStrategy;
import org.springframework.integration.aggregator.TimeoutCountSequenceSizeReleaseStrategy;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class IntegrationConfig {

    @Bean
    public ReleaseStrategy releaseStrategy() {
        return new TimeoutCountSequenceSizeReleaseStrategy(2, 200);
    }

    @Bean
    public DefaultAggregatingMessageGroupProcessor aggregate(){
        DefaultAggregatingMessageGroupProcessor aggregator = new DefaultAggregatingMessageGroupProcessor();
        return aggregator;
    }
}
