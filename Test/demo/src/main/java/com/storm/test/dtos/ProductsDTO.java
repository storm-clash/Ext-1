package com.storm.test.dtos;


import com.storm.test.entities.Base;
import com.storm.test.entities.Container;
import com.storm.test.entities.Sections;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductsDTO {

   // @NotNull(message = "The id should not be null")
   // private long id;
    @NotNull(message = "The size should not be null")
    private int size;
    @NotNull(message = "The color should not be null")
    @NotEmpty(message = "The color should not be empty")
    private String color;
    @NotNull(message = "The price should not be empty")
    private double price;
    @NotNull(message = "The amount should not be empty")
    private int amount;
    private boolean fragile;
    @NotNull(message = "The batch should not be null")
    @NotEmpty(message = "The batch should not be empty")
    private String batch;
    private Container type;
    private Sections sections;

 @Override
 public boolean equals(Object o) {
  if (this == o) return true;
  if (o == null || getClass() != o.getClass()) return false;
  if (!super.equals(o)) return false;
  ProductsDTO that = (ProductsDTO) o;
  return size == that.size && Double.compare(that.price, price) == 0 && amount == that.amount && fragile == that.fragile && Objects.equals(color, that.color) && Objects.equals(batch, that.batch) && type == that.type && Objects.equals(sections, that.sections);
 }

 @Override
 public int hashCode() {
  return Objects.hash(super.hashCode(), size, color, price, amount, fragile, batch, type, sections);
 }
}
