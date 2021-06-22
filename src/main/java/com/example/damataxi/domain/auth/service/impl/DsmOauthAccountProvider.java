package com.example.damataxi.domain.auth.service.impl;

import com.example.damataxi.domain.auth.retrofit.dto.DsmOauthResponse;
import com.example.damataxi.domain.auth.retrofit.AccountProviderConnection;
import com.example.damataxi.global.error.exception.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DsmOauthAccountProvider implements com.example.damataxi.domain.auth.service.DsmOauthAccountProvider {

    private final AccountProviderConnection accountProviderConnection;

    @Override
    public DsmOauthResponse searchAccount(String oAuthToken) {
        try {
            return accountProviderConnection.authenticateFromDsmOauth(oAuthToken)
                    .execute()
                    .body();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidTokenException();
        }

    }
}
