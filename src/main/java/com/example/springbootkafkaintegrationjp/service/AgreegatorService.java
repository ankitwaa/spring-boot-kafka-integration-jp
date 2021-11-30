package com.example.springbootkafkaintegrationjp.service;

import com.example.springbootkafkaintegrationjp.config.integration.IntegrationConfig;
import com.example.springbootkafkaintegrationjp.integration.CustomReleaseStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.aggregator.AggregatingMessageHandler;
import org.springframework.integration.aggregator.DefaultAggregatingMessageGroupProcessor;
import org.springframework.integration.annotation.*;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.expression.ValueExpression;
import org.springframework.integration.mongodb.inbound.MongoDbMessageSource;
import org.springframework.integration.mongodb.store.MongoDbMessageStore;
import org.springframework.integration.store.MessageGroupStore;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AgreegatorService {

    @Qualifier("batchChannel")
    @Autowired
    private DirectChannel directChannel;

    @Qualifier("groupDiscardChannel")
    @Autowired
    private DirectChannel groupDiscardChannel;

    @Autowired
    private IntegrationConfig integrationConfig;

    @ServiceActivator(inputChannel = "feedChannel")
    @Bean
    public MessageHandler aggregator(MongoDbMessageStore mongoDbMessageStore) {
        AggregatingMessageHandler aggregator =
                new AggregatingMessageHandler(new DefaultAggregatingMessageGroupProcessor(),
                        mongoDbMessageStore);
        aggregator.setOutputChannel(directChannel);
        aggregator.setGroupTimeoutExpression(new ValueExpression<>(10000));
        aggregator.setReleaseStrategy(new CustomReleaseStrategy(5000,1000000));
        aggregator.setTaskScheduler(integrationConfig.threadPoolTaskScheduler());
        aggregator.setDiscardChannel(groupDiscardChannel);
        return aggregator;
    }

    @ServiceActivator(inputChannel = "groupDiscardChannel")
    public void discardChannel(Message<?> groupMessage){
        log.info("Received Group message in discard Channel" + groupMessage);
    }

}
