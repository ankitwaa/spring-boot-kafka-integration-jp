package com.example.springbootkafkaintegrationjp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class SpringBootKafkaIntegrationJpApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootKafkaIntegrationJpApplication.class, args);

	}

}
