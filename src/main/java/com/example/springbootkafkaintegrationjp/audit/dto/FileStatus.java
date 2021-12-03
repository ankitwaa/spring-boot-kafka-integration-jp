package com.example.springbootkafkaintegrationjp.audit.dto;


import lombok.Data;

@Data
public class FileStatus {
    private String filename;
    private String messageCount;
    private String messageCountReceived;
    private String status;
}
