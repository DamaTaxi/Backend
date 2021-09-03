package com.example.damataxi.domain.mypage.controller;

import com.example.damataxi.domain.mypage.dto.request.MypageRequest;
import com.example.damataxi.domain.mypage.dto.response.MypageResponse;
import com.example.damataxi.domain.mypage.service.MypageService;
import com.example.damataxi.domain.taxiPot.dto.response.TaxiPotContentResponse;
import com.example.damataxi.global.security.AuthenticationFacade;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/mypage")
@RestController
public class MypageController {

    private final MypageService mypageService;
    private final AuthenticationFacade authenticationFacade;

    @ApiOperation(value = "유저 정보 설정 및 변경하기", notes = "유저의 정보를 설정 및 변경합니다 (유저)")
    @PatchMapping
    public void setSetting(@Valid @RequestBody MypageRequest request) {
        mypageService.setSetting(request, authenticationFacade.getUser());
    }

    @ApiOperation(value = "유저 정보 받아오기", notes = "유저 정보를 받아옵니다 (유저)")
    @GetMapping
    public MypageResponse getSetting() {
        return mypageService.getSetting(authenticationFacade.getUser());
    }

    @ApiOperation(value = "유저가 신청한 택시팟 정보 받아오기", notes = "유저가 신청한 택시팟 정보를 받아옵니다 (유저)")
    @GetMapping("/taxi-pot")
    public TaxiPotContentResponse getApplyTaxiPot() {
        return mypageService.getApplyTaxiPot(authenticationFacade.getUser());
    }
}
