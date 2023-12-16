package com.storm.test.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeDto {

    @NotNull(message = "The id should not be null")
    private long id;
    @NotNull(message = "The name should not be null")
    @NotEmpty(message = "The name should not be empty")
    private String name;
}
