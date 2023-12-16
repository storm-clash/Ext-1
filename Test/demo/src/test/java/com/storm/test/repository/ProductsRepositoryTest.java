package com.storm.test.repository;

import com.storm.test.entities.Container;
import com.storm.test.entities.Products;
import com.storm.test.entities.Sections;
import com.storm.test.entities.Type;
import com.storm.test.repositories.ProductRepository;
import com.storm.test.repositories.SectionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductsRepositoryTest {

    @Autowired
    private ProductRepository repository;
    @Autowired
    private SectionRepository sectionRepository;

    private Products productsTest;

    private Sections sec;


    @BeforeEach
    void setup(){
        Type type = new Type("Body_Care");
        sec = new Sections(50, type);
        productsTest = new Products(1,"red,",123,1,true,"we34", Container.PLASTIC,sec);
    }

    @Test
    void saveProduct(){
      Sections savedSection = sectionRepository.save(sec);
        Products productsSaved= repository.save(productsTest);

        //then - verificar salida
        assertThat(productsSaved).isNotNull();
        assertThat(productsSaved.getId()).isGreaterThan(0);
    }
    @Test
    void getAll(){

        var products = new Products(2,"yellow,",123,1,true,"we34", Container.PLASTIC,sec);

        Sections savedSection = sectionRepository.save(sec);
        repository.save(products);
        repository.save(productsTest);

        List<Products> productsListList = repository.findAll();

        assertThat(productsListList).isNotNull();
        assertThat(productsListList.size()).isEqualTo(2);

    }
    @Test
    void getProductById(){
        Sections savedSection = sectionRepository.save(sec);
        repository.save(productsTest);

        Optional<Products> saveProduct = repository.findById(productsTest.getId());

        assertThat(saveProduct).isNotNull();
    }

    @Test
    void updateProduct(){
        Sections savedSection = sectionRepository.save(sec);
        repository.save(productsTest);

        Products saveProduct = repository.findById(productsTest.getId()).get();

        saveProduct.setColor("whiteEgg");
        saveProduct.setAmount(10);
        saveProduct.setFragile(false);

        Products productsUpdated = repository.save(saveProduct);

        assertThat(productsUpdated.getColor()).isEqualTo("whiteEgg");
        assertThat(productsUpdated.getAmount()).isEqualTo(10);
        assertThat(productsUpdated.isFragile()).isEqualTo(false);


    }

    @Test
    void deleteUser(){
        Sections savedSection = sectionRepository.save(sec);
        repository.save(productsTest);
        sectionRepository.deleteById(sec.getId());
        repository.deleteById(productsTest.getId());

        Optional<Products> optionalProducts = repository.findById(productsTest.getId());

        assertThat(optionalProducts).isEmpty();
    }
}
