package com.example.springbootkafkaintegrationjp.config.kafka;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Data
@ConfigurationProperties("feed.inbound")
public class TopicConfiguration {
    private Map<String,Map<String,String>> topic;
}
