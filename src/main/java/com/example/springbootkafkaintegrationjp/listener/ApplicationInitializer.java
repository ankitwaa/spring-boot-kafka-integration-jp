package com.example.springbootkafkaintegrationjp.listener;

import com.example.springbootkafkaintegrationjp.config.kafka.TopicConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Slf4j
@Component
public class ApplicationInitializer {

    private ExecutorService executorService = Executors.newFixedThreadPool(10);
    private KafkaListenerFactory kafkaListenerFactory;
    private TopicConfiguration topicConfiguration;
    private List<Future<?>> futures = new ArrayList<>();
    private ApplicationContext applicationContext;

    @Value("${feed.region}")
    private String region;

    @Value("${feed.topic.concurrency:1}")
    private int threadCount;

    @Autowired
    public void setTopicConfiguration(TopicConfiguration topicConfiguration) {
        this.topicConfiguration = topicConfiguration;
    }

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Autowired
    public void setKafkaListenerFactory(KafkaListenerFactory kafkaListenerFactory) {
        this.kafkaListenerFactory = kafkaListenerFactory;
    }

    @PostConstruct
    public void initialize(){
        topicConfiguration.getTopic().get(region).forEach((key, value) -> {
            for (int i = 0; i < threadCount; i++) {
                log.info("creating listener for system:{}, topic:{}", key, value);
                FeedListener feedListener = applicationContext.getBean(FeedListener.class);
                feedListener.initialize(value, "group_" + value + "_" + UUID.randomUUID().toString());
                futures.add(executorService.submit(feedListener));
            }
        });
    }
}
