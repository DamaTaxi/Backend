package com.example.damataxi.domain.auth.controller;

import com.example.damataxi.domain.auth.dto.request.AdminLoginRequest;
import com.example.damataxi.domain.auth.dto.request.UserLoginRequest;
import com.example.damataxi.domain.auth.dto.response.TokenResponse;
import com.example.damataxi.domain.auth.dto.response.UserTokenResponse;
import com.example.damataxi.domain.auth.service.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/login")
@RestController
public class LoginController {

    private final AuthService authService;

    @ApiOperation(value = "유저 로그인", notes = "DSM OAUTH 토큰을 받아서 로그인합니다 (ALL)")
    @PostMapping
    public UserTokenResponse userLogin(@Valid @RequestBody UserLoginRequest request){
        return authService.userLogin(request.getAccessToken());
    }

    @ApiOperation(value = "어드민 로그인", notes = "어드민 아이디와 비밀번호를 받아서 로그인합니다 (ALL)")
    @PostMapping("/admin")
    public TokenResponse adminLogin(@Valid @RequestBody AdminLoginRequest request){
        return authService.adminLogin(request);
    }

    @ApiOperation(value = "테스트 유저 로그인", notes = "테스트용 유저의 토큰을 받아서 반환합니다 (ALL)")
    @PostMapping("/test")
    public TokenResponse testUserLogin(){
        return authService.getTestUserToken();
    }

}
