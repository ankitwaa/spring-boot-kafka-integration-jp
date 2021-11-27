package com.example.springbootkafkaintegrationjp.audit.integration;

import com.example.springbootkafkaintegrationjp.audit.repo.EventRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EventMetricsService {
    private EventRepo eventRepo;

    @Autowired
    public void setEventRepo(EventRepo eventRepo) {
        this.eventRepo = eventRepo;
    }

    
}
