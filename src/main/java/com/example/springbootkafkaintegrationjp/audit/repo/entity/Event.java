package com.example.springbootkafkaintegrationjp.audit.repo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("event")
@Data
public class Event {
    @Id
    private String messageId;
    private String recOrPublish;
    private String payload;
    private LocalDateTime receivedDate;
}
