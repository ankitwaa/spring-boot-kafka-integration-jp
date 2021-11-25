package com.example.springbootkafkaintegrationjp.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;

@ToString
@Data
public class Player {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
}
