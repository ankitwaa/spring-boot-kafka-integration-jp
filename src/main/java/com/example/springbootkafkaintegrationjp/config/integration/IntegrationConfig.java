package com.example.springbootkafkaintegrationjp.config.integration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.integration.aggregator.DefaultAggregatingMessageGroupProcessor;
import org.springframework.integration.mongodb.store.MongoDbMessageStore;
import org.springframework.integration.store.MessageGroupStore;
import org.springframework.integration.store.SimpleMessageStore;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class IntegrationConfig {

    @Bean
    public DefaultAggregatingMessageGroupProcessor aggregate() {
        DefaultAggregatingMessageGroupProcessor aggregator = new DefaultAggregatingMessageGroupProcessor();
        return aggregator;
    }

    @Bean
    public MessageGroupStore messageGroupStore() {
        MessageGroupStore store = new SimpleMessageStore();
        return store;
    }

    @Bean("mongodbMessageStore")
    public MongoDbMessageStore messageMongoGroupStore(MongoDatabaseFactory mongoDatabaseFactory) {
        MongoDbMessageStore store = new MongoDbMessageStore(mongoDatabaseFactory, "message_store");
        return store;
    }

    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler
                = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(5);
        threadPoolTaskScheduler.setThreadNamePrefix(
                "ThreadPoolTaskScheduler");
        threadPoolTaskScheduler.initialize();
        return threadPoolTaskScheduler;
    }

}
