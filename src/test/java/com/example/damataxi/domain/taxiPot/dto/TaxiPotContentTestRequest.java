package com.example.damataxi.domain.taxiPot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaxiPotContentTestRequest {

    private String target;
    private String meetingAt;
    private String place;
    private String content;
    private double latitude;
    private double longitude;
    private String title;
    private int amount;
    private String address;

}
