package com.example.damataxi.domain.taxiPot.dto;

import com.example.damataxi.domain.taxiPot.dto.response.UserContentResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class TaxiPotContentTestResponse {

    private String title;
    private String address;
    private UserContentResponse creator;
    private String target;
    private int reserve;
    private int all;
    private double latitude;
    private double longitude;
    private String place;
    private String meetingAt;
    private String createdAt;
    private String content;
    private List<UserContentResponse> users;

}
