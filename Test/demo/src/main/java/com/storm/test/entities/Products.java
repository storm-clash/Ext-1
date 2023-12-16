package com.storm.test.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Products extends Base {

    @Column(name = "size")
    @NotNull(message = "The size should not be null")
    private int size;

    @Column(name = "color")
    @NotNull(message = "The color should not be null")
    @NotEmpty(message = "The color should not be empty")
    private String color;

    @Column(name = "price")
    @NotNull(message = "The price should not be empty")
    private double price;

    @Column(name = "amount")
    @NotNull(message = "The amount should not be empty")
    private int amount;

    @Column(name = "fragile")
    @NotNull
    private boolean fragile;

    @Column(name = "batch")
    @NotNull(message = "The batch should not be null")
    @NotEmpty(message = "The batch should not be empty")
    private String batch;

    @Enumerated(EnumType.STRING)
    @Column(name="container")
    private Container type;

    @ManyToOne
    @JoinColumn(name="section_id", nullable=false)
    private Sections sections;

    public Products(Long id, int size, String color, double price, int amount, boolean fragile, String batch, Container type, Sections sections) {
        super(id);
        this.size = size;
        this.color = color;
        this.price = price;
        this.amount = amount;
        this.fragile = fragile;
        this.batch = batch;
        this.type = type;
        this.sections = sections;
    }
}
