package com.example.damataxi.domain.taxiPot.dto.request;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
public class TaxiPotContentRequest {
    private String target;
    @DateTimeFormat(pattern = "yyyy-MM-dd-HH:mm")
    private LocalDateTime meetingAt;
    private String place;
    private String content;
    private double latitude;
    private double longitude;
    private int amount;
    private int price;
}
