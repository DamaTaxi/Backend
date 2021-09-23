package com.example.damataxi.domain.mypage.dto;

import com.example.damataxi.domain.mypage.dto.response.CreatorContentResponse;
import com.example.damataxi.domain.taxiPot.dto.response.UserContentResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class MyTaxiPotContentTestResponse {

    private String title;
    private CreatorContentResponse creator;
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
