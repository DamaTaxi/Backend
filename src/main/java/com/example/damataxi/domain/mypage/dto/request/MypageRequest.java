package com.example.damataxi.domain.mypage.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MypageRequest {
    @NotNull
    private String tel;
    @NotNull
    private double latitude;
    @NotNull
    private double longitude;
}
