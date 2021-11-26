package com.example.springbootkafkaintegrationjp.config.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.aggregator.AggregatingMessageHandler;
import org.springframework.integration.aggregator.DefaultAggregatingMessageGroupProcessor;
import org.springframework.integration.aggregator.ReleaseStrategy;
import org.springframework.integration.aggregator.TimeoutCountSequenceSizeReleaseStrategy;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.expression.ValueExpression;
import org.springframework.integration.store.MessageGroupStore;
import org.springframework.integration.store.SimpleMessageStore;
import org.springframework.messaging.MessageHandler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

import java.sql.Time;

@Component
@Configuration
public class IntegrationConfig {

    @Qualifier("batchChannel")
    @Autowired
    private DirectChannel directChannel;

    @Bean
    public ReleaseStrategy releaseStrategy() {
        return new TimeoutCountSequenceSizeReleaseStrategy(2, 200);
    }

    @Bean
    public DefaultAggregatingMessageGroupProcessor aggregate(){
        DefaultAggregatingMessageGroupProcessor aggregator = new DefaultAggregatingMessageGroupProcessor();
        return aggregator;
    }

    @Bean
    public MessageGroupStore messageGroupStore(){
        MessageGroupStore store = new SimpleMessageStore();
        store.expireMessageGroups(1000);
        return store;
    }

    public ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        ThreadPoolTaskScheduler threadPoolTaskScheduler
                = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(5);
        threadPoolTaskScheduler.setThreadNamePrefix(
                "ThreadPoolTaskScheduler");
        threadPoolTaskScheduler.initialize();
        return threadPoolTaskScheduler;
    }

    @ServiceActivator(inputChannel = "feedChannel")
    @Bean
    public MessageHandler aggregator(MessageGroupStore jdbcMessageGroupStore) {
        AggregatingMessageHandler aggregator =
                new AggregatingMessageHandler(new DefaultAggregatingMessageGroupProcessor(),
                        jdbcMessageGroupStore);
        aggregator.setOutputChannel(directChannel);
        aggregator.setGroupTimeoutExpression(new ValueExpression<>(100000));
        aggregator.setReleaseStrategy(new TimeoutCountSequenceSizeReleaseStrategy(2,1000));
        aggregator.setTaskScheduler(threadPoolTaskScheduler());
        return aggregator;
    }
}
