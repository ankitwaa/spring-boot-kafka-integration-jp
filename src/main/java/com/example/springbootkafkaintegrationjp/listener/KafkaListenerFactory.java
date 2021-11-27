package com.example.springbootkafkaintegrationjp.listener;

import com.example.springbootkafkaintegrationjp.config.kafka.CustomDeserializable;
import com.example.springbootkafkaintegrationjp.config.kafka.JsonSerdes;
import com.example.springbootkafkaintegrationjp.domain.FeedEvent;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Properties;

@Slf4j
@Component
public class KafkaListenerFactory {

    @Value("${kafka.broker}")
    private String broker;


    public KafkaConsumer<String, FeedEvent> kafkaConsumer(String topic, String consumerGroup){
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, broker);
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, CustomDeserializable.class);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroup);

        KafkaConsumer<String,FeedEvent> kafkaConsumer = new KafkaConsumer<String, FeedEvent>(properties);
        kafkaConsumer.subscribe(List.of(topic), new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> collection) {
                log.info("partition revoked:{}", collection);
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> collection) {
                log.info("partition assigned:{}", collection);
            }
        });
        return kafkaConsumer;
    }

}
