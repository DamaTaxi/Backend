package com.example.damataxi.domain.taxiPot.dto;

import java.util.List;

public class TaxiPotPageTestResponse {

    private long totalElements;

    private int totalPages;

    private List<TaxiPotListContentTestResponse> content;

    public long getTotalElements() {
        return this.totalElements;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public List<TaxiPotListContentTestResponse> getContent() {
        return content;
    }
}
