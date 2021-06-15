package com.example.damataxi.domain.mypage.controller;

import com.example.damataxi.domain.mypage.dto.request.MypageRequest;
import com.example.damataxi.domain.mypage.dto.response.MypageResponse;
import com.example.damataxi.domain.mypage.service.MypageService;
import com.example.damataxi.global.error.exception.UserNotFoundException;
import com.example.damataxi.global.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/mapage")
@RestController
public class MypageController {

    private final MypageService mypageService;
    private final AuthenticationFacade authenticationFacade;

    @PatchMapping
    public void setSetting(@Valid @RequestBody MypageRequest request) {
        mypageService.setSetting(request, authenticationFacade.getUser());
    }

    @GetMapping
    public MypageResponse getSetting() {
        return mypageService.getSetting(authenticationFacade.getUser());
    }
}
