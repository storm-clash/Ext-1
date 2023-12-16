package com.storm.test.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class RequestDTO {

    private List<SearchRequestDTO> searchRequestDTO;

    private GlobalOperator globalOperator;

   public enum GlobalOperator{
        AND, OR;
    }
}
