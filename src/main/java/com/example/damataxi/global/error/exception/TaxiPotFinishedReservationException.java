package com.example.damataxi.global.error.exception;

import com.example.damataxi.global.error.ErrorCode;

public class TaxiPotFinishedReservationException extends GlobalException{
    public TaxiPotFinishedReservationException(int id) {
        super("taxi pot " + id + " is finished reservation", ErrorCode.TAXI_POT_FINISHED_RESERVATION);
    }
}
