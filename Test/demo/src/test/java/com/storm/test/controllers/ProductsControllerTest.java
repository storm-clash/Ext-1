package com.storm.test.controllers;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willAnswer;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.storm.test.auth.AuthenticationRequest;
import com.storm.test.auth.AuthenticationResponse;
import com.storm.test.auth.AuthenticationService;
import com.storm.test.auth.RegisterRequest;
import com.storm.test.config.JwtService;
import com.storm.test.entities.Container;
import com.storm.test.entities.Products;
import com.storm.test.entities.Sections;
import com.storm.test.entities.Type;
import com.storm.test.repositories.ProductRepository;
import com.storm.test.repositories.SectionRepository;
import com.storm.test.services.FiltersSpecifications;
import com.storm.test.services.ProductsServiceImpl;
import com.storm.test.services.SectionsServiceImpl;
import com.storm.test.services.TypeServiceImpl;
import com.storm.test.user.Role;
import com.storm.test.user.User;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest
public class ProductsControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductsServiceImpl service;

    @MockBean
    private ProductRepository repository;

    @MockBean
    private SectionRepository sectionRepository;

    @MockBean
    private FiltersSpecifications<Products> filtersSpecifications;

    @MockBean
    private FiltersSpecifications<Sections>specifications;

    @MockBean
    private SectionsServiceImpl sectionsService;

    @MockBean
    private TypeServiceImpl typeService;


    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;



    private Products productsTest;

    private Type type;
    private Sections sections;

    @Value("${api.endpoint.base-url}")
    String baseUrl;

    String token;

    @BeforeEach
    void setUp() throws Exception {
        ResultActions actions = this.mockMvc
                .perform(post("http://localhost:3000/api/v1/products")
                        .with(httpBasic("Jesus","1234")));
        MvcResult mvcResult = actions.andDo(print()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        JSONObject json = new JSONObject(contentAsString);
        this.token = "Bearer " + json.getJSONObject("data").getString("token");
    }

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
    void saveProductTest() throws Exception {
        var request = new AuthenticationRequest("ifarocks26@gmail.com","1234");
        var response1 = new AuthenticationResponse("msg","token");
        var register = new RegisterRequest("test","test","ifarocks26@gmail.com","1234",Role.ADMIN);
       User user= User.builder()
                .name("test")
                .lastName("test")
                .email("test@gmail.com")
                .password("1234")
                .role(Role.MANAGER)
                .build();
        given(authenticationService.register(register));
        given(jwtService.generateToken(user));
        given(authenticationService.authenticate(request)).willReturn(response1);


        given(service.save(any(Products.class))).willReturn(productsTest)
        .willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("http://localhost:3000/api/v1/products")
                .header(HttpHeaders.AUTHORIZATION, this.token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productsTest))
        );

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size",is(productsTest.getSize())))
                .andExpect(jsonPath("$.amount",is(productsTest.getAmount())))
                .andExpect(jsonPath("$.color",is(productsTest.getColor())));
    }

    @Test
    void getAll() throws Exception{
        Sections sec = new Sections(1L,50,type);
        Products p = new Products(1L,1,"red,",123,1,true,"we34",Container.PLASTIC,sec);
        Products p1 = new Products(2L,2,"yellow,",123,1,true,"we34",Container.PLASTIC,sec);
        List<Products> productsList = new ArrayList<>();
        productsList.add(p);
        productsList.add(p1);

        var request = new AuthenticationRequest("ifarocks26@gmail.com","1234");
        var response1 = new AuthenticationResponse("smg","token");
        var register = new RegisterRequest("test","test","ifarocks26@gmail.com","1234", Role.ADMIN);



        given(service.findAll()).willReturn(productsList);


        ResultActions response = mockMvc.perform(get("http://localhost:3000/api/v1/products")
                .header(HttpHeaders.AUTHORIZATION, this.token));


        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",is(productsList.size())));
    }

    @Test
    void getOne() throws Exception {
        long productId = productsTest.getId();
        var sec = new Sections(1L,50,type);
        Products p = new Products(1L,1,"red,",123,1,true,"we34",Container.PLASTIC,sec);



        given(service.findById(p.getId())).willReturn(Optional.of(p).get());

        ResultActions response = mockMvc.perform(get("http://localhost:3000/api/v1/products/{id}",p.getId())
                .header(HttpHeaders.AUTHORIZATION, this.token));

        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size",is(productsTest.getSize())))
                .andExpect(jsonPath("$.amount",is(productsTest.getAmount())))
                .andExpect(jsonPath("$.color",is(productsTest.getColor())));
    }
}
