package com.example.damataxi.domain.taxiPot.service.impl;

import com.example.damataxi.domain.auth.domain.User;
import com.example.damataxi.domain.auth.domain.UserRepository;
import com.example.damataxi.domain.taxiPot.domain.Reservation;
import com.example.damataxi.domain.taxiPot.domain.ReservationRepository;
import com.example.damataxi.domain.taxiPot.domain.TaxiPot;
import com.example.damataxi.domain.taxiPot.domain.TaxiPotRepository;
import com.example.damataxi.global.error.exception.ApplyNotFoundException;
import com.example.damataxi.global.error.exception.TaxiPotNotFoundException;
import com.example.damataxi.global.error.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaxiPotRefreshFacade {

    private final UserRepository userRepository;
    private final TaxiPotRepository taxiPotRepository;
    private final ReservationRepository reservationRepository;

    public User refreshUser(User user) {
        return userRepository.findById(user.getGcn())
                .orElseThrow(()-> new UserNotFoundException(user.getUsername()));
    }

    public TaxiPot refreshTaxiPot(TaxiPot taxiPot) {
        return taxiPotRepository.findById(taxiPot.getId())
                .orElseThrow(()-> new TaxiPotNotFoundException(taxiPot.getId()));
    }

    public Reservation refreshReservation(Reservation reservation) {
        return reservationRepository.findById(reservation.getId())
                .orElseThrow(()-> new ApplyNotFoundException(reservation.getUser().getUsername()));
    }
}
