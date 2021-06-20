package com.example.damataxi.domain.auth.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class UserLoginRequest {
    @NotNull
    private String accessToken;
}
