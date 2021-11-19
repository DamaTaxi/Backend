package com.example.damataxi.domain.auth.util.api.client;

import com.example.damataxi.domain.auth.util.api.dto.TokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "tokenClient", url = "https://developer-api.dsmkr.com")
public interface TokenClient {

    @GetMapping("/v1/info/basic/")
    TokenResponse getUserInfo(@RequestHeader("Authorization") String value);

}
