package com.example.damataxi.domain.auth.util.api;

import com.example.damataxi.global.error.exception.ApiRequestException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignClientErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ApiRequestException decode(final String methodKey, Response response) {

        String message = "Server failed to request other server.";

        if (response.body() != null) {
            try {
                message = objectMapper.readTree(response.body().toString())
                        .get("msg").asText();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return new ApiRequestException(response.status(), message);
    }

}
