package com.example.damataxi.domain.taxiPot.dto.response;

import com.example.damataxi.domain.taxiPot.domain.TaxiPot;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class TaxiPotSlideContentResponse {
    private int id;

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

    @ApiModelProperty(value = "택시 팟 도착지 주소", example = "주소주소주소주소소주맥주소맥")
    private String address;

    public static TaxiPotSlideContentResponse from(TaxiPot taxiPot){
        return TaxiPotSlideContentResponse.builder()
                .id(taxiPot.getId())
                .target(taxiPot.getTarget().name())
                .reserve(taxiPot.getReservations().size())
                .all(taxiPot.getAmount())
                .latitude(taxiPot.getDestinationLatitude())
                .longitude(taxiPot.getDestinationLongitude())
                .title(taxiPot.getTitle())
                .address(taxiPot.getAddress())
                .build();
    }
}
