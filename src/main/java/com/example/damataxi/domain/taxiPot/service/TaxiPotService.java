package com.example.damataxi.domain.taxiPot.service;

import com.example.damataxi.domain.auth.domain.User;
import com.example.damataxi.domain.taxiPot.dto.request.TaxiPotContentRequest;
import com.example.damataxi.domain.taxiPot.dto.response.*;

import java.util.List;

public interface TaxiPotService {
    TaxiPotInfoResponse getTaxiPotInfo();
    List<TaxiPotSlideContentResponse> getTaxiPotSlideList();
    List<TaxiPotSlideContentResponse> getTaxiPotSlideList(User user);
    TaxiPotPage getTaxiPotList(int size, int page);
    TaxiPotPage getTaxiPotList(User user, int size, int page);
    TaxiPotContentResponse getTaxiPotContent(int id);
    void makeTaxiPot(User user,TaxiPotContentRequest request);
    void changeTaxiPotContent(User user,TaxiPotContentRequest request, int id);
    void deleteTaxiPot(User user,int id);
    void applyTaxiPot(User user, int id);
    void cancelApplyTaxiPot(User user, int id);
}
