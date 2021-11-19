package com.example.damataxi.domain.auth.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class UserLoginRequest {

    @ApiModelProperty(value = "code", example = "code")
    @NotNull
    private String code;

}
