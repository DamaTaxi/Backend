package com.example.damataxi.domain.taxiPot.dto;

public class TaxiPotListContentTestResponse {
    private int id;
    private String creator;
    private String target;
    private int price;
    private int reserve;
    private int all;
    private double latitude;
    private double longitude;
    private String place;
    private String meetingAt;
    private String createdAt;
    private String title;

    public String getTarget() {
        return target;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
