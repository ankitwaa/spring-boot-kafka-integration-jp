package com.example.springbootkafkaintegrationjp.domain;

import lombok.Data;

@Data
public class KafkaMessageProcessedInfo {
    private String fileName;
    private String filePath;
    private String msgId;
    private long messageCount;
}
