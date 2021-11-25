package com.example.springbootkafkaintegrationjp.config.kafka;

import com.example.springbootkafkaintegrationjp.domain.FeedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;

public class JsonSerdes {

    private final static ObjectMapper objectMapper = new ObjectMapper();

    public static Serde<FeedEvent> playerSerde() {
        Serializer<FeedEvent> serializer = (topic, data) -> {
            try {
                return objectMapper.writeValueAsBytes(data);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        };

        Deserializer<FeedEvent> deserializer = (topic, data) -> {
            try {
                return objectMapper.readValue(data, FeedEvent.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        };

        return Serdes.serdeFrom(serializer, deserializer);
    }

}
