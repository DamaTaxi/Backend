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
        if ( !reservationRepository.findByIdUserGcn(user.getGcn()).isEmpty() ) {
            throw new AlreadyApplyException();
        }
    }

    public void checkIsCreator(User user, int id) {
        TaxiPot taxiPot = getTaxiPot(id);
        if(!(taxiPot.getCreator().getGcn().equals(user.getGcn()))) {
            throw new NotCreatorException(user.getUsername());
        }
    }

    public void checkChangePossible(int id) {
        TaxiPot taxiPot = taxiPotRepository.findById(id).orElseThrow(()-> { throw new TaxiPotNotFoundException(id); } );
        if(taxiPot.getReservations().size()>1){
            throw new ImpossibleChangeException();
        }
    }

    public TaxiPot getTaxiPot(int id) {
        return taxiPotRepository.findById(id)
                .orElseThrow(()-> { throw new TaxiPotNotFoundException(id); });
    }

    public Reservation getReservation(User user, int id) {
        return reservationRepository.findById(new ReservationId(id, user.getGcn()))
                .orElseThrow(()-> { throw new ApplyNotFoundException(user.getUsername()); });
    }
}
