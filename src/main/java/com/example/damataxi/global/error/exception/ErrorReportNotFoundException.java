package com.example.damataxi.global.error.exception;

import com.example.damataxi.global.error.ErrorCode;

public class ErrorReportNotFoundException extends GlobalException{
    public ErrorReportNotFoundException(int id){
        super("error " + id + " is not found", ErrorCode.ERROR_REPORT_NOT_FOUND);
    }
}
