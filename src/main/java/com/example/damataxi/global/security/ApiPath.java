package com.example.damataxi.global.security;

import lombok.Getter;

@Getter
public class ApiPath {

    public static final String[] SWAGGER_PATH = { // all
            "/swagger-ui/*",
            "/swagger-ui/**",
            "/v2/api-docs",
            "/swagger-resources/**"
    };

    public static final String[] LOGIN_PATH = { // all
            "/login",
            "/login/admin",
            "/token-refresh"
    };

    public static final String[] MYPAGE_PATH = { // user
            "/mypage"
    };

    public static final String[] TAXI_POT_GET_PATH = { // all // GET
            "/taxi-pot*",
            "/taxi-pot/*"
    };

    public static final String[] TAXI_POT_POST_PATH = { // user // POST
            "/taxi-pot",
            "/taxi-pot/sub"
    };

    public static final String[] TAXI_POT_PATCH_PATH = { // user // PATCH
            "/taxi-pot/*"
    };

    public static final String[] TAXI_POT_DELETE_PATH = { // user // DELETE
            "/taxi-pot/*"
    };

    public static final String[] ERROR_REPORT_POST_PATH = { // all // POST
            "/error-report"
    };

    public static final String[] ERROR_REPORT_GET_PATH = { // admin // GET
            "/error-report",
            "/error-report/*"
    };

    public static final String[] ERROR_REPORT_DELETE_PATH = { // admin // DELETE
            "/error-report"
    };

    public static final String[] SUGGESION_POST_PATH = { // all // POST
            "/suggestion"
    };

    public static final String[] SUGGESION_GET_PATH = { // admin // GET
            "/suggestion",
            "/suggestion/*"
    };

    public static final String[] SUGGESION_DELETE_PATH = { // admin // DELETE
            "/suggestion"
    };
}
