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
    TAXI_POT_FINISHED_RESERVATION(401, "S002", "Taxi Pot Finished Reservation"),
    ALREADY_APPLY(401, "S003", "Already Apply"),
    IMPOSSIBLE_CHANGE(401, "S004", "Impossible Change"),
    USER_NOT_FOUND(404, "S005", "User Not Found"),
    TAXI_POT_NOT_FOUND(404, "S006", "Taxi Pot Not Found"),
    APPLY_NOT_FOUND(404, "S007", "Did Not Apply"),
    ERROR_REPORT_NOT_FOUND(404, "S008", "Error Report Not Found"),
    SUGGESTION_NOT_FOUND(404, "S009", "Suggestion Not Found");

    private final int status;
    private final String code;
    private final String message;

}
