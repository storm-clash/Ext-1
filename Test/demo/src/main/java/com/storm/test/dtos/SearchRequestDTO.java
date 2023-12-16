package com.storm.test.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class SearchRequestDTO {

    private String column;
    private String value;

    Operation operation;

    public enum Operation {
        EQUAL, LIKE, IN, GREATER_THAN, LESS_THAN;
    }
}
