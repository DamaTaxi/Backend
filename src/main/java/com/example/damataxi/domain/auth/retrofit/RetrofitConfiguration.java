package com.example.damataxi.domain.auth.retrofit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class RetrofitConfiguration {

    @Bean
    public AccountProviderConnection retrofit() {
        return new Retrofit.Builder()
                .baseUrl("https://developer-api.dsmkr.com")
                .addConverterFactory(JacksonConverterFactory.create(new ObjectMapper()))
                .build()
                .create(AccountProviderConnection.class);
    }
}