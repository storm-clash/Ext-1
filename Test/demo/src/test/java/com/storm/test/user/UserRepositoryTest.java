package com.storm.test.user;

import com.storm.test.entities.Container;
import com.storm.test.entities.Products;
import com.storm.test.entities.Sections;
import com.storm.test.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @Test
    void checkProduct(){

        //give
        String email ="ifarocks26@gmail.com";
        User user = new User(
                "jesus","garcia",email,"1234",Role.ADMIN
        );
        underTest.save(user);
        //when

        Optional<User> exits = underTest.findByEmail("ifarocks26@gmail.com");
        //then
         // assertThat(exits).isTrue();
        assertEquals(1,underTest.findAll().size());

    }
}
