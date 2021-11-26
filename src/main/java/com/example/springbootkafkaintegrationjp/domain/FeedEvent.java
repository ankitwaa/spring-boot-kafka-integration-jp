package com.example.springbootkafkaintegrationjp.domain;

import lombok.Data;

@Data
public class FeedEvent {
    private String key;
    private String value;
    private String orderId;
    private long count;
}
