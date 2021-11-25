package com.example.springbootkafkaintegrationjp.domain;

import lombok.Data;

import java.util.List;

@Data
public class BatchFeedEvent {
    private List<FeedEvent> feedEventList;
}
