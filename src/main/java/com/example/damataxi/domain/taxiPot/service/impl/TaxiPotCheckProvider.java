package com.example.damataxi.domain.taxiPot.service.impl;

import com.example.damataxi.domain.auth.domain.User;
import com.example.damataxi.domain.taxiPot.domain.*;
import com.example.damataxi.global.error.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaxiPotCheckProvider {

    private final TaxiPotRepository taxiPotRepository;
    private final ReservationRepository reservationRepository;

    public void checkAlreadyApply(User user) {
        if (reservationRepository.findByIdUserEmail(user.getEmail()).isPresent()) {
            throw new AlreadyApplyException();
        }
    }

    public void checkIsCreator(User user, int id) {
        TaxiPot taxiPot = getTaxiPot(id);
        if(!(taxiPot.getCreator().getEmail().equals(user.getEmail()))) {
            throw new NotCreatorException(user.getUsername());
        }
    }

    public void checkChangePossible(int id) {
        TaxiPot taxiPot = taxiPotRepository.findById(id).orElseThrow(()-> new TaxiPotNotFoundException(id));
        if(taxiPot.getReservations().size()>1){
            throw new ImpossibleChangeException();
        }
    }

    public void checkCorrectTarget(TaxiPotTarget userGrade, TaxiPotTarget taxiPotTarget){
        if(!(userGrade.equals(taxiPotTarget) || taxiPotTarget.equals(TaxiPotTarget.ALL))) {
            throw new InvalidInputValueException();
        }
    }

    public TaxiPot getTaxiPot(int id) {
        return taxiPotRepository.findById(id)
                .orElseThrow(()-> new TaxiPotNotFoundException(id));
    }

    public Reservation getReservation(User user, int id) {
        return reservationRepository.findById(new ReservationId(id, user.getEmail()))
                .orElseThrow(()-> new ApplyNotFoundException(user.getUsername()));
    }

    public void checkTaxiPotFinishedReservation(TaxiPot taxiPot){
        if(taxiPot.getReservations().size()==taxiPot.getAmount()) {
            throw new TaxiPotFinishedReservationException(taxiPot.getId());
        }
    }
}
