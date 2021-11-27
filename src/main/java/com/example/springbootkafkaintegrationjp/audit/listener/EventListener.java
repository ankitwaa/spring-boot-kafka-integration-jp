package com.example.springbootkafkaintegrationjp.audit.listener;

import com.example.springbootkafkaintegrationjp.audit.integration.EventChannelGateway;
import com.example.springbootkafkaintegrationjp.domain.Event;
import com.example.springbootkafkaintegrationjp.domain.FeedEvent;
import com.example.springbootkafkaintegrationjp.integration.ChannelGateway;
import com.example.springbootkafkaintegrationjp.listener.KafkaListenerFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.integration.IntegrationMessageHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Scope(value = ScannedGenericBeanDefinition.SCOPE_PROTOTYPE)
@Component
public class EventListener implements Runnable {
    private boolean paused;
    private String topic;
    private KafkaConsumer kafkaConsumer;
    private KafkaListenerFactory kafkaListenerFactory;
    private EventChannelGateway channelGateway;

    @Autowired
    public void setChannelGateway(EventChannelGateway channelGateway) {
        this.channelGateway = channelGateway;
    }

    @Autowired
    public void setKafkaListenerFactory(KafkaListenerFactory kafkaListenerFactory) {
        this.kafkaListenerFactory = kafkaListenerFactory;
    }

    public void initialize(String topic, String cg, Class<?> deserializer) {
        this.topic = topic;
        kafkaConsumer = kafkaListenerFactory.kafkaConsumer(topic, cg, deserializer);
    }

    @Override
    public void run() {
        while (!paused) {
            try {
                log.info("reading from topic:{}", topic);
                ConsumerRecords<String, Event> records = kafkaConsumer.poll(10000);
                records.forEach(cr -> {
                    log.info("Sending data to Validation Channel");
                    channelGateway.sendToEventChannel(MessageBuilder.withPayload(cr.value()).build());
                    kafkaConsumer.commitAsync();
                });
            }catch (Exception exception){
                log.error("error {}", exception);
                kafkaConsumer.commitAsync();
            }
        }
    }
}
