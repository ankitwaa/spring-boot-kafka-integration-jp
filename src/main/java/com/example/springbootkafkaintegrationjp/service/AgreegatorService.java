package com.example.springbootkafkaintegrationjp.service;

import com.example.springbootkafkaintegrationjp.config.integration.IntegrationConfig;
import com.example.springbootkafkaintegrationjp.domain.BatchFeedEvent;
import com.example.springbootkafkaintegrationjp.domain.FeedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.aggregator.AggregatingMessageHandler;
import org.springframework.integration.aggregator.DefaultAggregatingMessageGroupProcessor;
import org.springframework.integration.aggregator.TimeoutCountSequenceSizeReleaseStrategy;
import org.springframework.integration.annotation.*;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.expression.ValueExpression;
import org.springframework.integration.store.MessageGroup;
import org.springframework.integration.store.MessageGroupStore;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;

@Slf4j
@Component
public class AgreegatorService {

    @Qualifier("batchChannel")
    @Autowired
    private DirectChannel directChannel;

    @Autowired
    private IntegrationConfig integrationConfig;

    @ServiceActivator(inputChannel = "feedChannel")
    @Bean
    public MessageHandler aggregator(MessageGroupStore jdbcMessageGroupStore) {
        AggregatingMessageHandler aggregator =
                new AggregatingMessageHandler(new DefaultAggregatingMessageGroupProcessor(),
                        jdbcMessageGroupStore);
        aggregator.setOutputChannel(directChannel);
        aggregator.setGroupTimeoutExpression(new ValueExpression<>(10000));
        aggregator.setReleaseStrategy(new TimeoutCountSequenceSizeReleaseStrategy(2,1000));
        aggregator.setTaskScheduler(integrationConfig.threadPoolTaskScheduler());
        return aggregator;
    }

}
