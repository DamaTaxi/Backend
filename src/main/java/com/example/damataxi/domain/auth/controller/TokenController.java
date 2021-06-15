package com.example.damataxi.domain.auth.controller;

import com.example.damataxi.domain.auth.dto.request.TokenRefreshRequest;
import com.example.damataxi.domain.auth.dto.response.TokenResponse;
import com.example.damataxi.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/token-refresh")
@RestController
public class TokenController {

    private final AuthService authService;

    @PatchMapping
    public TokenResponse tokenRefresh(@Valid @RequestBody TokenRefreshRequest request){
        return authService.tokenRefresh(request);
    }
}
