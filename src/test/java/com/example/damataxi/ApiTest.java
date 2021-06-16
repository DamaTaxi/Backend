package com.example.damataxi;

import com.example.damataxi.global.security.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public class ApiTest extends IntegrationTest{

    @Autowired
    private MockMvc mvc;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private final ObjectMapper objectMapper = new ObjectMapper();

    protected ResultActions requestMvc(MockHttpServletRequestBuilder method, Object obj) throws Exception {
        return mvc.perform(method
                .content(objectMapper
                        .registerModule(new JavaTimeModule())
                        .writeValueAsString(obj))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    protected ResultActions requestMvc(MockHttpServletRequestBuilder method) throws Exception {
        return mvc.perform(method
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    protected String makeAccessToken(String user){
        return jwtTokenProvider.generateAccessToken(user);
    }

    protected String makeRefreshToken(String user){
        return jwtTokenProvider.generateRefreshToken(user);
    }
}