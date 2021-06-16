package com.example.damataxi.domain.taxiPot.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
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
}
