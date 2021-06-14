package com.example.damataxi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = DamaTaxiApplication.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
public class IntegrationTest {

    @Autowired
    private MockMvc mvc;

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
}