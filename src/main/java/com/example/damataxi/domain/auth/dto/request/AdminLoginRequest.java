package com.example.damataxi.domain.auth.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminLoginRequest {

    @ApiModelProperty(value = "어드민 아이디", example = "adminId")
    @NonNull
    private String id;

    @ApiModelProperty(value = "어드민 비밀번호", example = "adminPassword")
    @NotNull
    private String password;

}
