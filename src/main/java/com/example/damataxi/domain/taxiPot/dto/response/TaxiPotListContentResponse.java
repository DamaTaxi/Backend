package com.example.damataxi.domain.taxiPot.dto.response;

import com.example.damataxi.domain.taxiPot.domain.TaxiPot;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class TaxiPotListContentResponse {
    private int id;
    private String creator;
    private String target;
    private int price;
    private int reserve;
    private int all;
    private double latitude;
    private double longitude;
    private String place;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-mm:ss")
    private LocalDateTime meetingAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd-mm:ss")
    private LocalDateTime createdAt;

    public static TaxiPotListContentResponse from(TaxiPot taxiPot){
        return TaxiPotListContentResponse.builder()
                .id(taxiPot.getId())
                .creator(taxiPot.getCreator().getUsername())
                .target(taxiPot.getTarget().name())
                .price(taxiPot.getPrice())
                .reserve(taxiPot.getReservations().size())
                .all(taxiPot.getAmount())
                .latitude(taxiPot.getDestinationLatitude())
                .longitude(taxiPot.getDestinationLongitude())
                .place(taxiPot.getPlace())
                .meetingAt(taxiPot.getMeetingAt())
                .createdAt(taxiPot.getCreatedAt())
                .build();
    }
}
