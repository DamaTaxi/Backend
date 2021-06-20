package com.example.damataxi.domain.auth.controller;

import com.example.damataxi.domain.auth.dto.request.AdminLoginRequest;
import com.example.damataxi.domain.auth.dto.request.UserLoginRequest;
import com.example.damataxi.domain.auth.dto.response.TokenResponse;
import com.example.damataxi.domain.auth.dto.response.UserTokenResponse;
import com.example.damataxi.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/login")
@RestController
public class LoginController {

    private final AuthService authService;

    @PostMapping
    public UserTokenResponse userLogin(@Valid @RequestBody UserLoginRequest request){
        return authService.userLogin(request.getAccessToken());
    }

    @PostMapping("/admin")
    public TokenResponse adminLogin(@Valid @RequestBody AdminLoginRequest request){
        return authService.adminLogin(request);
    }
}
