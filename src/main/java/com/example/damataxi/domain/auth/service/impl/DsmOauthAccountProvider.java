package com.example.damataxi.domain.auth.service.impl;

import com.example.damataxi.domain.auth.retrofit.dto.DsmOauthResponse;
import com.example.damataxi.domain.auth.retrofit.AccountProviderConnection;
import com.example.damataxi.domain.auth.service.AccountProvider;
import com.example.damataxi.global.error.exception.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DsmOauthAccountProvider implements AccountProvider {

    private final AccountProviderConnection accountProviderConnection;

    @Override
    public DsmOauthResponse searchAccount(String oAuthToken) {
        DsmOauthResponse response = null;

        try{
            response =
                    accountProviderConnection.authenticateFromDsmOauth(oAuthToken)
                    .execute()
                    .body();
        } catch (Exception e) {
            e.printStackTrace();
            throw new InvalidTokenException();
        }

        return response;
    }
}
