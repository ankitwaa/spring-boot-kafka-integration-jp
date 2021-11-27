package com.example.springbootkafkaintegrationjp.audit.integration;

import com.example.springbootkafkaintegrationjp.audit.repo.FileMetricEventRepo;
import com.example.springbootkafkaintegrationjp.audit.repo.entity.FileMetricEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FileMetricsListener {

    private FileMetricEventRepo fileMetricEventRepo;

    @Autowired
    public void setFileMetricEventRepo(FileMetricEventRepo fileMetricEventRepo) {
        this.fileMetricEventRepo = fileMetricEventRepo;
    }

    @ServiceActivator(inputChannel = "fileMetricsChannel", outputChannel = "nullChannel")
    public void persistFileMetrics(Message<FileMetricEvent> fileMetricEventMessage){
        log.info("saving file metrics");
        fileMetricEventRepo.save(fileMetricEventMessage.getPayload());
    }
}
