package com.example.springbootkafkaintegrationjp.listener;

import com.example.springbootkafkaintegrationjp.domain.FeedEvent;
import com.example.springbootkafkaintegrationjp.integration.ChannelGateway;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.IntegrationMessageHeaderAccessor;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Scope(value = ScannedGenericBeanDefinition.SCOPE_PROTOTYPE)
@Component
public class FeedListener implements Runnable {
    private boolean paused;
    private String topic;
    private KafkaConsumer kafkaConsumer;
    private KafkaListenerFactory kafkaListenerFactory;
    private ChannelGateway channelGateway;

    @Autowired
    public void setChannelGateway(ChannelGateway channelGateway) {
        this.channelGateway = channelGateway;
    }

    @Autowired
    public void setKafkaListenerFactory(KafkaListenerFactory kafkaListenerFactory) {
        this.kafkaListenerFactory = kafkaListenerFactory;
    }

    public void initialize(String topic, String cg) {
        this.topic = topic;
        kafkaConsumer = kafkaListenerFactory.kafkaConsumer(topic, cg);
    }

    @Override
    public void run() {
        while (!paused) {
            try {
                log.info("reading from topic:{}", topic);
                ConsumerRecords<String, FeedEvent> records = kafkaConsumer.poll(10000);
                records.forEach(cr -> {
                    log.info("Sending data to Validation Channel");
                    FeedEvent feedEvent = cr.value();
                    feedEvent.setKey(cr.key());
                    channelGateway.sendToValidationChannel(MessageBuilder.withPayload(feedEvent).setHeader(IntegrationMessageHeaderAccessor.CORRELATION_ID, feedEvent.getOrderId()).build());
                    kafkaConsumer.commitAsync();
                });
            }catch (Exception exception){
                log.error("error {}", exception);
                kafkaConsumer.commitAsync();
            }
        }
    }
}
