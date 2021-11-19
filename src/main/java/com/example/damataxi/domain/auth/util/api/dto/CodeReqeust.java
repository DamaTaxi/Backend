package com.example.damataxi.domain.auth.util.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CodeReqeust {

    private String client_id;
    private String client_secret;
    private String code;

}
