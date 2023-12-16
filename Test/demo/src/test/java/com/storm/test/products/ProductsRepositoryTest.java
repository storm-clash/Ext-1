package com.storm.test.products;

import com.storm.test.entities.Container;
import com.storm.test.entities.Products;
import com.storm.test.entities.Sections;
import com.storm.test.entities.Type;
import com.storm.test.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.ClassBasedNavigableIterableAssert.assertThat;


public class ProductsRepositoryTest {

    @Autowired
    private ProductRepository underTest;

    @Test
    void checkProduct(){
        Sections sections = new Sections();
        //give
        Products products = new Products(
                20,"red",20,2,true,"PX12", Container.CARDBOARD, sections
        );
        underTest.save(products);
        //when

      List<Products> exits = underTest.searchFilter("red",20);
        //then
      //  assertThat(exits).isTrue();

    }
}
