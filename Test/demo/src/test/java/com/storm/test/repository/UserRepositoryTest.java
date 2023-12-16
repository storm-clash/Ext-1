package com.storm.test.repository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.LIST;

import com.storm.test.user.Role;
import com.storm.test.user.User;
import com.storm.test.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    private User userTest;

    @BeforeEach
    void setup(){
        userTest= User.builder()
                .name("test")
                .lastName("test")
                .email("test@gmail.com")
                .password("1234")
                .role(Role.MANAGER)
                .build();
    }

    @DisplayName("Test to save a user")
    @Test
    void saveUser(){

        //given-condicion previa

        User user = User.builder()
                .name("jesus")
                .lastName("garcia")
                .email("ifarocks26@gmail.com")
                .password("1234")
                .role(Role.ADMIN)
                .build();

        //when - accion

        User userSave= repository.save(user);

        //then - verificar salida
        assertThat(userSave).isNotNull();
        assertThat(userSave.getId()).isGreaterThan(0);

    }
    @Test
    void getAll(){

        User user = User.builder()
                .name("diana")
                .lastName("garcia")
                .email("diana@gmail.com")
                .password("1234")
                .role(Role.ADMIN)
                .build();

        repository.save(user);
        repository.save(userTest);

        List<User> userList = repository.findAll();

        assertThat(userList).isNotNull();
        assertThat(userList.size()).isEqualTo(2);

    }
    @Test
    void getUserByEmail(){
        repository.save(userTest);

        Optional<User> saveUser = repository.findByEmail(userTest.getEmail());

        assertThat(saveUser).isNotNull();
    }

    @Test
    void updateUser(){
        repository.save(userTest);

        User saveUser = repository.findById(userTest.getId()).get();

        saveUser.setEmail("ifExist@gmail.com");
        saveUser.setName("update");
        saveUser.setLastName("updateLast");

        User userUpdated = repository.save(saveUser);

        assertThat(userUpdated.getEmail()).isEqualTo("ifExist@gmail.com");
        assertThat(userUpdated.getName()).isEqualTo("update");
        assertThat(userUpdated.getLastName()).isEqualTo("updateLast");


    }

    @Test
    void deleteUser(){
        repository.save(userTest);

        repository.deleteById(userTest.getId());

        Optional<User> optionalUser = repository.findById(userTest.getId());

        assertThat(optionalUser).isEmpty();
    }
}
