package com.storm.test.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

import com.storm.test.entities.*;
import com.storm.test.repositories.ProductRepository;
import com.storm.test.repositories.SectionRepository;
import com.storm.test.user.Role;
import com.storm.test.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductsServiceTest {

    @Mock
    private ProductRepository repository;

    @InjectMocks
    private ProductsServiceImpl service;

    @Mock
    private SectionRepository sectionRepository;

    @InjectMocks

    private SectionsServiceImpl sectionsService;


    private Products productsTest;

    private Type type;
    private Sections sections;
    @BeforeEach
    void setup(){

        type= new Type("Body_Care");
        sections =   Sections.builder()
                .size(50)
                .typeProduct(type)

                .build();
        productsTest= Products.builder()

                .size(1)
                .color("platinum_Grey")
                .price(100)
                .amount(1)
                .batch("xp12")
                .fragile(true)
                .type(Container.PLASTIC)
                .sections(sections)
                .build();
    }

    @Test
    void saveProduct(){
        Sections sec = new Sections(1L,50,type);
        Products p = new Products(1L,1,"red,",123,1,true,"we34",Container.PLASTIC,sec);
       given(sectionRepository.findById(p.getSections().getId())).willReturn(Optional.of(sec));

        given(repository.findById(p.getId()))
                .willReturn(Optional.of(p));
        given(repository.save(p)).willReturn(p);

        Products productSaved = service.save(p);

        assertThat(productSaved).isNotNull();
    }

    @Test
    void getAll() throws Exception {

        Products products= Products.builder()

                .size(1)
                .color("platinum_Grey")
                .price(100)
                .amount(1)
                .batch("xp12")
                .fragile(true)
                .type(Container.PLASTIC)
                .sections(sections)
                .build();
        given(repository.findAll()).willReturn(List.of(productsTest,products));

        List<Products> productsList = service.findAll();

        assertThat(productsList).isNotNull();
        assertThat(productsList.size()).isEqualTo(2);
    }

    @Test
    void getProductById() throws Exception {
        given(repository.findById(productsTest.getId())).willReturn(Optional.of(productsTest));

        Products productsSaved = service.findById(productsTest.getId());

        assertThat(productsSaved).isNotNull();
    }

    @Test
    void updateProduct() throws Exception {
        Products p = new Products(1L,1,"red,",123,1,true,"we34",Container.PLASTIC,sections);
        given(repository.findById(p.getId())).willReturn(Optional.of(p));


        p.setColor("Blue");
        p.setAmount(5);

        Products updatedProduct = service.update(1L,p);

        assertThat(p.getColor()).isEqualTo("Blue");
        assertThat(p.getAmount()).isEqualTo(5);
    }

    @Test
    void deleteProduct() throws Exception {
        Products p = new Products(1L,1,"red,",123,1,true,"we34",Container.PLASTIC,sections);
        long productId = p.getId();

            given(repository.existsById(productId)).willReturn(true);
        willDoNothing().given(repository).deleteById(productId);


        service.delete(productId);

        verify(repository,times(1)).deleteById(productId);
    }
}
