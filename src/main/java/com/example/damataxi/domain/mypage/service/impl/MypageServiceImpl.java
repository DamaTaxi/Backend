package com.example.damataxi.domain.mypage.service.impl;

import com.example.damataxi.domain.auth.domain.User;
import com.example.damataxi.domain.auth.domain.UserRepository;
import com.example.damataxi.domain.mypage.dto.request.MypageRequest;
import com.example.damataxi.domain.mypage.dto.response.MyTaxiPotContentResponse;
import com.example.damataxi.domain.mypage.dto.response.MypageResponse;
import com.example.damataxi.domain.mypage.service.MypageService;
import com.example.damataxi.domain.taxiPot.domain.Reservation;
import com.example.damataxi.domain.taxiPot.domain.TaxiPot;
import com.example.damataxi.domain.taxiPot.domain.TaxiPotRepository;
import com.example.damataxi.domain.taxiPot.dto.response.TaxiPotContentResponse;
import com.example.damataxi.global.error.exception.TaxiPotNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MypageServiceImpl implements MypageService {

    private final UserRepository userRepository;
    private final TaxiPotRepository taxiPotRepository;

    @Override
    public void setSetting(MypageRequest request, User user) {
        user.setTel(request.getTel());
        user.setLatitude(request.getLatitude());
        user.setLongitude(request.getLongitude());
        user.setAddress(request.getAddress());
        userRepository.save(user);
    }

    @Override
    public MypageResponse getSetting(User user) {
        return MypageResponse.from(user);
    }

    @Override
    @Transactional
    public MyTaxiPotContentResponse getApplyTaxiPot(User user) {
        Reservation reservation = user.getReservation();
        if(reservation!=null){
            TaxiPot taxiPot = taxiPotRepository.findById(reservation.getTaxiPot().getId()).get();
            return MyTaxiPotContentResponse.from(taxiPot, taxiPot.getReservations(), user.getEmail());
        }
        throw new TaxiPotNotFoundException();
    }

}
