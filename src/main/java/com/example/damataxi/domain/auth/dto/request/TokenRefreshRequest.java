package com.example.damataxi.domain.auth.dto.request;

import lombok.Getter;

@Getter
public class TokenRefreshRequest {
    private String refreshToken;
}
