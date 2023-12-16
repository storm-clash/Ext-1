package com.storm.test.dtos;

import com.storm.test.entities.Base;
import com.storm.test.entities.Products;
import com.storm.test.entities.Type;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionDTO {

   // @NotNull(message = "The id should not be null")
   // private long id;
    @NotNull(message = "The size should not be null")
    private double size;
    private Type typeProduct;
    private Set<Products> products;
}
