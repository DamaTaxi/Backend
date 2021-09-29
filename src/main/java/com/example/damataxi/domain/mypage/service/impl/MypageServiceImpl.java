package com.example.damataxi.domain.mypage.service.impl;

import com.example.damataxi.domain.auth.domain.User;
import com.example.damataxi.domain.auth.domain.UserRepository;
import com.example.damataxi.domain.mypage.dto.request.MypageRequest;
import com.example.damataxi.domain.mypage.dto.response.MyTaxiPotContentResponse;
import com.example.damataxi.domain.mypage.dto.response.MypageResponse;
import com.example.damataxi.domain.mypage.service.MypageService;
import com.example.damataxi.domain.taxiPot.domain.Reservation;
import com.example.damataxi.domain.taxiPot.dto.response.TaxiPotContentResponse;
import com.example.damataxi.global.error.exception.TaxiPotNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService {

    private final UserRepository userRepository;

    @Override
    public void setSetting(MypageRequest request, User user) {
        user.setTel(request.getTel());
        user.setLatitude(request.getLatitude());
        user.setLongitude(request.getLongitude());
        userRepository.save(user);
    }

    @Override
    public MypageResponse getSetting(User user) {
        return MypageResponse.from(user);
    }

    @Override
    public MyTaxiPotContentResponse getApplyTaxiPot(User user) {
        Reservation reservation = user.getReservation();
        if(reservation!=null){
            return MyTaxiPotContentResponse.from(reservation.getTaxiPot(), user.getEmail());
        }
        throw new TaxiPotNotFoundException();
    }

}
