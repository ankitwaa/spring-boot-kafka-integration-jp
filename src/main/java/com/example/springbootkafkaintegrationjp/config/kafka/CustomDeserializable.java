package com.example.springbootkafkaintegrationjp.config.kafka;

import com.example.springbootkafkaintegrationjp.domain.FeedEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;
import java.util.Map;

public class CustomDeserializable implements Deserializer<FeedEvent> {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        Deserializer.super.configure(configs, isKey);
    }

    @Override
    public FeedEvent deserialize(String topic, Headers headers, byte[] data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }

    @Override
    public FeedEvent deserialize(String s, byte[] bytes) {
        try {
            return objectMapper.readValue(bytes, FeedEvent.class);
        } catch (IOException e) {
           throw new RuntimeException("Deserializable");
        }
    }
}
