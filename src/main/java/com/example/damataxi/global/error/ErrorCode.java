package com.example.damataxi.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(500, "C001", "Internal Server Error"),
    INVALID_INPUT_VALUE(400, "C002", "Invalid Input Value"),

    INVALID_TOKEN(401, "A001", "Invalid Token"),

    NOT_CREATOR(401, "S001", "Not Creator"),
    ALREADY_APPLY(401, "S002", "Already Apply"),
    IMPOSSIBLE_CHANGE(401, "S003", "Impossible Change"),
    USER_NOT_FOUND(404, "S004", "User Not Found"),
    TAXI_POT_NOT_FOUND(404, "S005", "Taxi Pot Not Found"),
    APPLY_NOT_FOUND(404, "S006", "Did Not Apply"),
    ERROR_REPORT_NOT_FOUND(404, "S007", "Error Report Not Found"),
    SUGGESTION_NOT_FOUND(404, "S008", "Suggestion Not Found");

    private final int status;
    private final String code;
    private final String message;

}
