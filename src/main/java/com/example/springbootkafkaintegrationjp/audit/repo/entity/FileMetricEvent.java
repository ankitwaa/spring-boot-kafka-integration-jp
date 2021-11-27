package com.example.springbootkafkaintegrationjp.audit.repo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("file_events")
@Data
public class FileMetricEvent {
    @Id
    private String fileName;
    private List<String> messageIds;
    private String status;
    private long receivedCount;
    private long expectedCount;
}
