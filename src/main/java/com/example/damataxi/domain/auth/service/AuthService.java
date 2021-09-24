package com.example.damataxi.domain.auth.service;

import com.example.damataxi.domain.auth.dto.request.AdminLoginRequest;
import com.example.damataxi.domain.auth.dto.request.TokenRefreshRequest;
import com.example.damataxi.domain.auth.dto.response.TokenResponse;
import com.example.damataxi.domain.auth.dto.response.UserTokenResponse;

public interface AuthService {
    UserTokenResponse userLogin(String oauthAccessToken);
    TokenResponse adminLogin(AdminLoginRequest adminLoginRequest);
    TokenResponse tokenRefresh(TokenRefreshRequest tokenRefreshRequest);
    TokenResponse getTestUserToken();
}
