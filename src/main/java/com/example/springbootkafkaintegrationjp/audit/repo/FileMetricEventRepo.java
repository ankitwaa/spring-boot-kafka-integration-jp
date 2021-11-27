package com.example.springbootkafkaintegrationjp.audit.repo;

import com.example.springbootkafkaintegrationjp.audit.repo.entity.Event;
import com.example.springbootkafkaintegrationjp.audit.repo.entity.FileMetricEvent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileMetricEventRepo extends MongoRepository<FileMetricEvent, String> {
    Optional<Event> findByFileName(String fileName);
}
