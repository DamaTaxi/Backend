package com.example.damataxi.domain.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserTokenResponse {
    private String accessToken;
    private String refreshToken;
    private Boolean firstLogin;
}
