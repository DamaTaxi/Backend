package com.example.damataxi.domain.auth.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserTokenResponse {

    @ApiModelProperty(value = "ACCESS 토큰", example = "accessToken")
    private String accessToken;

    @ApiModelProperty(value = "REFRESH 토큰", example = "refreshToken")
    private String refreshToken;

    @ApiModelProperty(value = "유저 첫 로그인 여부", example = "true")
    private Boolean firstLogin;
}
