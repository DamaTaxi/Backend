package com.example.damataxi.domain.auth.retrofit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DsmOauthResponse {
    private String name;
    private String gcn;
    private String email;
}
