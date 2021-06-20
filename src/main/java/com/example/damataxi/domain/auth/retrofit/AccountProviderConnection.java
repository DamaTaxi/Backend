package com.example.damataxi.domain.auth.retrofit;

import com.example.damataxi.domain.auth.retrofit.dto.DsmOauthResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface AccountProviderConnection {

    @Headers(value = {"accept: application/json", "content-type: application/json"})
    @GET("/v1/info/basic")
    Call<DsmOauthResponse> authenticateFromDsmOauth(@Header("Authorization") String token);
}
