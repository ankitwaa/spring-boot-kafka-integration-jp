package com.example.springbootkafkaintegrationjp.domain;

import lombok.Data;
import org.springframework.messaging.Message;

import java.util.List;

@Data
public class BatchFeedEvent {
    private Message<List<FeedEvent>> feedEventList;
}
