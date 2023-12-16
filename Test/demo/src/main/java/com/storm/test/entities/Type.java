package com.storm.test.entities;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "type_Product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Type extends Base{

    @Column(name = "name")
    @NotNull(message = "The name should not be null")
    @NotEmpty(message = "The name should not be empty")
    private String name;
}
