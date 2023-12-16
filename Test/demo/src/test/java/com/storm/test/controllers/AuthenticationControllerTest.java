package com.storm.test.controllers;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willAnswer;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.storm.test.auth.AuthenticationRequest;
import com.storm.test.auth.AuthenticationResponse;
import com.storm.test.auth.AuthenticationService;
import com.storm.test.auth.RegisterRequest;
import com.storm.test.config.JwtAuthentificationFilter;
import com.storm.test.repositories.ProductRepository;
import com.storm.test.services.FiltersSpecifications;
import com.storm.test.services.ProductsServiceImpl;
import com.storm.test.user.Role;
import com.storm.test.user.User;
import netscape.javascript.JSObject;


import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest
public class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;



    @MockBean
    private AuthenticationService service;

    @MockBean
    private JwtAuthentificationFilter filter;

    @Value("${api.endpoint.base-url}")
    String baseUrl;

    String token;
    @Autowired
    private ObjectMapper objectMapper;
    @BeforeEach
    void setUp() throws Exception {
    ResultActions actions = this.mockMvc
            .perform(post(this.baseUrl + "auth/register")
                    .with(httpBasic("jesus","1234")));
    MvcResult mvcResult = actions.andDo(print()).andReturn();
    String contentAsString = mvcResult.getResponse().getContentAsString();
        JSONObject json = new JSONObject(contentAsString);
        this.token = "Bearer " + json.getJSONObject("data").getString("token");
    }


    @Test
    void testAuthentication() throws Exception {
        var register = new RegisterRequest("test","test","ifarocks26@gmail.com","1234",Role.ADMIN);

        given(service.register(any(RegisterRequest.class)))
                .willAnswer((invocation) ->invocation.getArgument(0));


        ResultActions response = mockMvc.perform(post("http://localhost:3000/api/v1/auth/register")
                .header(HttpHeaders.AUTHORIZATION, this.token)
                        .with(jwt())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(register))
        );

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is(register.getName())))
                .andExpect(jsonPath("$.lastname",is(register.getLastname())))
                .andExpect(jsonPath("$.email",is(register.getEmail())));


    }





}
