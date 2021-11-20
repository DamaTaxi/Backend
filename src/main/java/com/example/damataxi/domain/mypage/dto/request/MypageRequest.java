package com.example.damataxi.domain.mypage.dto.request;

import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "유저 전화번호", example = "01012030149")
    @NotNull
    private String tel;

    @ApiModelProperty(value = "자주가는 곳 위도", example = "12.3456")
    @NotNull
    private double latitude;

    @ApiModelProperty(value = "자주가는 곳 경도", example = "34.5678")
    @NotNull
    private double longitude;

    @ApiModelProperty(value = "자주가는 곳 주소", example = "대전광역시 유성구 장동 가정북로 76")
    @NotNull
    private String address;
}
