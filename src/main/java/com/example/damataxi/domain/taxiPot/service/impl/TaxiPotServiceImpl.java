package com.example.damataxi.domain.taxiPot.service.impl;

import com.example.damataxi.domain.auth.domain.User;
import com.example.damataxi.domain.auth.domain.UserRepository;
import com.example.damataxi.domain.taxiPot.domain.*;
import com.example.damataxi.domain.taxiPot.dto.request.TaxiPotContentRequest;
import com.example.damataxi.domain.taxiPot.dto.response.TaxiPotContentResponse;
import com.example.damataxi.domain.taxiPot.dto.response.TaxiPotInfoResponse;
import com.example.damataxi.domain.taxiPot.dto.response.TaxiPotListContentResponse;
import com.example.damataxi.domain.taxiPot.service.TaxiPotService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaxiPotServiceImpl implements TaxiPotService {

    private final TaxiPotCheckProvider taxiPotCheckProvider;

    private final UserRepository userRepository;
    private final TaxiPotRepository taxiPotRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public TaxiPotInfoResponse getTaxiPotInfo() {
        int all = taxiPotRepository.findAll().size();
        int reserve = reserveCount();
        return new TaxiPotInfoResponse(all, reserve);
    }

    private int reserveCount() {
        int count = 0;
        for( TaxiPot taxiPot : taxiPotRepository.findAll() ) {
            if(taxiPot.getReservations().size() == taxiPot.getAll()) {
                count = count + 1;
            }
        }
        return count;
    }

    @Override
    public List<TaxiPotListContentResponse> getTaxiPotList(Authentication auth, int size, int page) {
        // TODO 모든 택시 팟 리스트
        return null;
    }

    @Override
    public TaxiPotContentResponse getTaxiPotContent(int id) {
        TaxiPot taxiPot = taxiPotRepository.getById(id);
        return TaxiPotContentResponse.from(taxiPot);
    }

    @Override
    public void makeTaxiPot(User user,TaxiPotContentRequest request) {
        taxiPotCheckProvider.checkAlreadyApply(user);

        TaxiPot taxiPot = TaxiPot.builder()
                .creator(user.getGcn())
                .price(request.getPrice())
                .target(TaxiPotTarget.valueOf(request.getTarget()))
                .createdAt(LocalDateTime.now())
                .meetingAt(request.getMeetingAt())
                .place(request.getPlace())
                .content(request.getContent())
                .destinationLatitude(request.getLatitude())
                .destinationLongitude(request.getLongitude())
                .all(request.getAmount())
                .user(user)
                .build();
        taxiPotRepository.save(taxiPot);

        Reservation reservation = Reservation.builder()
                .id(new ReservationId(taxiPot.getId(), user.getGcn()))
                .user(user)
                .taxiPot(taxiPot)
                .build();
        reservationRepository.save(reservation);

        List<Reservation> reservations = new ArrayList<>();
        reservations.add(reservation);
        taxiPot.setReservations(reservations);
        taxiPotRepository.save(taxiPot);

        user.setTaxiPot(taxiPot);
        user.setReservation(reservation);
        userRepository.save(user);
    }

    @Override
    public void changeTaxiPotContent(User user, TaxiPotContentRequest request, int id) {
        taxiPotCheckProvider.checkIsCreator(user, id);
        taxiPotCheckProvider.checkChangePossible(id);

        TaxiPot taxiPot = TaxiPot.builder()
                .id(id)
                .creator(user.getGcn())
                .price(request.getPrice())
                .target(TaxiPotTarget.valueOf(request.getTarget()))
                .createdAt(LocalDateTime.now())
                .meetingAt(request.getMeetingAt())
                .place(request.getPlace())
                .content(request.getContent())
                .destinationLatitude(request.getLatitude())
                .destinationLongitude(request.getLongitude())
                .all(request.getAmount())
                .user(user)
                .build();
        taxiPotRepository.save(taxiPot);
    }

    @Override
    public void deleteTaxiPot(User user, int id) {
        taxiPotCheckProvider.checkIsCreator(user, id);

        reservationRepository.deleteById(new ReservationId(id, user.getGcn()));
        taxiPotRepository.deleteById(id);
        user.setReservedPot(null);
        user.setTaxiPot(null);
        user.setReservation(null);
    }

    @Override
    public void applyTaxiPot(User user, int id) {
        TaxiPot taxiPot = taxiPotCheckProvider.getTaxiPot(id);

        Reservation reservation = Reservation.builder()
                .id(new ReservationId(taxiPot.getId(), user.getGcn()))
                .user(user)
                .taxiPot(taxiPot)
                .build();
        reservationRepository.save(reservation);
        taxiPot.setReservations(reservationRepository.findByIdPotId(id));
        taxiPotRepository.save(taxiPot);
        user.setReservedPot(id);
        user.setReservation(reservation);
        userRepository.save(user);
    }

    @Override
    public void cancleApplyTaxiPot(User user, int id) {
        TaxiPot taxiPot =taxiPotCheckProvider.getTaxiPot(id);
        Reservation reservation = taxiPotCheckProvider.getReservation(user, id);

        reservationRepository.delete(reservation);
        taxiPot.setReservations(reservationRepository.findByIdPotId(id));
        taxiPotRepository.save(taxiPot);
        user.setReservedPot(null);
        user.setReservation(null);
        userRepository.save(user);
    }
}
