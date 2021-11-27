package com.example.springbootkafkaintegrationjp.domain;

import lombok.Data;

import java.util.List;

@Data
public class FileMetric {
    private String fileName;
    private List<String> messageIds;
    private String status;
    private int receivedCount;
    private int expectedCount;
}
