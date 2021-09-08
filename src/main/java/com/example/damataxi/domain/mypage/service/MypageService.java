package com.example.damataxi.domain.mypage.service;

import com.example.damataxi.domain.auth.domain.User;
import com.example.damataxi.domain.mypage.dto.request.MypageRequest;
import com.example.damataxi.domain.mypage.dto.response.MyTaxiPotContentResponse;
import com.example.damataxi.domain.mypage.dto.response.MypageResponse;
import com.example.damataxi.domain.taxiPot.dto.response.TaxiPotContentResponse;

public interface MypageService {
    void setSetting(MypageRequest request, User user);
    MypageResponse getSetting(User user);
    MyTaxiPotContentResponse getApplyTaxiPot(User user);
}
