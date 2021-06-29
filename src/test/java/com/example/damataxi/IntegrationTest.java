package com.example.damataxi;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = DamaTaxiApplication.class)
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
public class IntegrationTest {
}
