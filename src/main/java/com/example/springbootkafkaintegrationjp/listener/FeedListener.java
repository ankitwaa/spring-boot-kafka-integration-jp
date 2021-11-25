package com.example.springbootkafkaintegrationjp.listener;

import com.example.springbootkafkaintegrationjp.domain.FeedEvent;
import com.example.springbootkafkaintegrationjp.integration.ChannelGateway;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.context.annotation.Scope;
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
            log.info("reading from topic:{}", topic);
            ConsumerRecords<String, String> records = kafkaConsumer.poll(2000);
            records.forEach(cr -> {
                log.info("Sending data to Validation Channel");
                FeedEvent feedEvent = new FeedEvent();
                feedEvent.setKey(cr.key());
                feedEvent.setValue(cr.value());
                channelGateway.sendToValidationChannel(MessageBuilder.withPayload(feedEvent).build());
            });

        }
    }
}
