package com.example.damataxi.domain.auth.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TokenRefreshRequest {

    @ApiModelProperty(value = "REFRESH 토큰", example = "refreshToken")
    @NotNull
    private String refreshToken;

}
