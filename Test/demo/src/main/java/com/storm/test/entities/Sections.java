package com.storm.test.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "section")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sections extends Base {

    @Column(name = "size")
    @NotNull(message = "The size should not be null")
    private double size;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_type")
    private Type typeProduct;

    @OneToMany(mappedBy="sections")
    private Set<Products> products;

   /* @OneToMany(cascade = CascadeType.PERSIST,orphanRemoval = true)
    @JoinTable(
            name = "section-products",
            joinColumns = @JoinColumn(name = "section_id"),
            inverseJoinColumns = @JoinColumn(name = "products_id")
    )
    private List<Products> products = new ArrayList<>();*/

    public Sections(Long id, double size, Type typeProduct) {
        super(id);
        this.size = size;
        this.typeProduct = typeProduct;

    }
    public Sections( double size, Type typeProduct) {

        this.size = size;
        this.typeProduct = typeProduct;

    }
}
