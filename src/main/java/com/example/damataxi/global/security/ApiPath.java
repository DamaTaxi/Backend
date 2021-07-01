package com.example.damataxi.global.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ApiPath {

    SWAGGER_PATH(new String[]{
            "/swagger-ui/*",
            "/swagger-ui/**",
            "/v2/api-docs",
            "/swagger-resources/**"}),

    LOGIN_PATH(new String[]{
            "/login",
            "/login/admin",
            "/token-refresh"
    }),

    MYPAGE_PATH(new String[]{
            "/mypage"
    }),

    TAXI_POT_GET_PATH(new String[]{
            "/taxi-pot*",
            "/taxi-pot/*"
    }),

    TAXI_POT_POST_PATH(new String[]{
            "/taxi-pot",
            "/taxi-pot/sub"
    }),

    TAXI_POT_PATCH_PATH(new String[]{
            "/taxi-pot/*"
    }),

    TAXI_POT_DELETE_PATH(new String[]{
            "/taxi-pot/*"
    }),

    ERROR_REPORT_POST_PATH(new String[]{
            "/error-report"
    }),

    ERROR_REPORT_GET_PATH(new String[]{
            "/error-report",
            "/error-report/*"
    }),

    ERROR_REPORT_DELETE_PATH(new String[]{
            "/error-report"
    }),

    SUGGESTION_POST_PATH(new String[]{
            "/suggestion"
    }),

    SUGGESTION_GET_PATH(new String[]{
            "/suggestion",
            "/suggestion/*"
    }),

    SUGGESTION_DELETE_PATH(new String[]{
            "/suggestion"
    });

    private final String[] apiPath;
}
