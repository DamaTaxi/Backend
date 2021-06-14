package com.example.damataxi.domain.auth.service;

import com.example.damataxi.domain.auth.retrofit.dto.DsmOauthResponse;

public interface AccountProvider {
    DsmOauthResponse searchAccount(String oAuthToken);
}
