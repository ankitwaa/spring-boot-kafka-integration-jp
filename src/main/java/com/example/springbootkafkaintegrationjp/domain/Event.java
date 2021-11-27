package com.example.springbootkafkaintegrationjp.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Event {
    private String messageId;
    private String recOrPublish;
    private String payload;
    private LocalDateTime receivedDate;
}
