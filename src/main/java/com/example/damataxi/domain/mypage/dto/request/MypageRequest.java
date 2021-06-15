package com.example.damataxi.domain.mypage.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
public class MypageRequest {
    @NotNull
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$")
    private String tel;
    @NotNull
    private double latitude;
    @NotNull
    private double longitude;
}
