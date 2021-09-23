package com.example.damataxi.domain.taxiPot.dto.response;

import com.example.damataxi.domain.taxiPot.domain.TaxiPot;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class TaxiPotListContentResponse {
    @ApiModelProperty(value = "택시 팟 아이디", example = "1")
    private int id;

    @ApiModelProperty(value = "택시 팟 생성자", example = "김재현")
    private String creator;

    @ApiModelProperty(value = "택시 팟 대상자", example = "ALL")
    private String target;

    @ApiModelProperty(value = "택시 팟 예약자 수", example = "3")
    private int reserve;

    @ApiModelProperty(value = "택시 팟 모집할 사람 수", example = "4")
    private int all;

    @ApiModelProperty(value = "택시 팟 도착지 위도", example = "12.3456")
    private double latitude;

    @ApiModelProperty(value = "택시 팟 도착지 경도", example = "34.5678")
    private double longitude;

    @ApiModelProperty(value = "택시 팟 도착지 이름", example = "가나다라마바사노래방")
    private String title;

    @ApiModelProperty(value = "택시 팟 약속 장소", example = "사감실")
    private String place;

    @ApiModelProperty(value = "택시 팟 약속 날짜 및 시간", example = "2020-12-03-03:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-mm:ss")
    private LocalDateTime meetingAt;

    @ApiModelProperty(value = "택시 팟 생성 날짜 및 시간", example = "2020-06-21-09:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-mm:ss")
    private LocalDateTime createdAt;

    public static TaxiPotListContentResponse from(TaxiPot taxiPot){
        return TaxiPotListContentResponse.builder()
                .id(taxiPot.getId())
                .creator(taxiPot.getCreator().getUsername())
                .target(taxiPot.getTarget().name())
                .reserve(taxiPot.getReservations().size())
                .all(taxiPot.getAmount())
                .latitude(taxiPot.getDestinationLatitude())
                .longitude(taxiPot.getDestinationLongitude())
                .title(taxiPot.getTitle())
                .place(taxiPot.getPlace())
                .meetingAt(taxiPot.getMeetingAt())
                .createdAt(taxiPot.getCreatedAt())
                .build();
    }
}
