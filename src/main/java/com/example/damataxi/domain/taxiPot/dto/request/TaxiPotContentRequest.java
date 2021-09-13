package com.example.damataxi.domain.taxiPot.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaxiPotContentRequest {

    @ApiModelProperty(value = "택시 팟 대상자", example = "ALL")
    private String target;

    @ApiModelProperty(value = "택시 팟 약속 날짜 및 시간", example = "2020-12-03-03:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd-HH:mm")
    private LocalDateTime meetingAt;

    @ApiModelProperty(value = "택시 팟 약속 장소", example = "사감실")
    private String place;

    @ApiModelProperty(value = "택시 팟 상세 정보", example = "몰래 노래방 가실래요..? 그런 사람들만 신청해주세요")
    private String content;

    @ApiModelProperty(value = "택시 팟 도착지 위도", example = "12.3456")
    private double latitude;

    @ApiModelProperty(value = "택시 팟 도착지 경도", example = "34.5678")
    private double longitude;

    @ApiModelProperty(value = "택시 팟 도착지 이름", example = "가나다라마바사노래방")
    private String title;

    @ApiModelProperty(value = "택시 팟 사람 수", example = "4")
    private int amount;

    @ApiModelProperty(value = "택시 팟 가격", example = "12000")
    private int price;

    @ApiModelProperty(value = "도착지 주소", example = "주소주소주소주소")
    private String address;
}
