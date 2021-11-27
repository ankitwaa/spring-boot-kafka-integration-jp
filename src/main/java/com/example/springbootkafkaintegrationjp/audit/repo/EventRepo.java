package com.example.springbootkafkaintegrationjp.audit.repo;

import com.example.springbootkafkaintegrationjp.audit.repo.entity.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepo extends MongoRepository<Event, String> {
    Optional<Event> findByMessageId(String messageId);
}
