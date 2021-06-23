package com.example.damataxi.domain.taxiPot.service.impl;

import com.example.damataxi.domain.auth.domain.User;
import com.example.damataxi.domain.auth.domain.UserRepository;
import com.example.damataxi.domain.taxiPot.domain.*;
import com.example.damataxi.domain.taxiPot.dto.request.TaxiPotContentRequest;
import com.example.damataxi.domain.taxiPot.dto.response.TaxiPotContentResponse;
import com.example.damataxi.domain.taxiPot.dto.response.TaxiPotInfoResponse;
import com.example.damataxi.domain.taxiPot.dto.response.TaxiPotListContentResponse;
import com.example.damataxi.domain.taxiPot.service.TaxiPotService;
import com.example.damataxi.global.error.exception.ApplyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaxiPotServiceImpl implements TaxiPotService {

    private final TaxiPotCheckProvider taxiPotCheckProvider;
    private final TaxiPotRefreshFacade taxiPotRefreshFacade;

    private final UserRepository userRepository;
    private final TaxiPotRepository taxiPotRepository;
    private final ReservationRepository reservationRepository;

    @Override
    public TaxiPotInfoResponse getTaxiPotInfo() {
        List<TaxiPot> allTaxiPot = taxiPotRepository.findAll();
        int all = allTaxiPot.size();
        int reserve = reserveCount(allTaxiPot);
        return new TaxiPotInfoResponse(all, reserve);
    }

    private int reserveCount(List<TaxiPot> allTaxiPot) {
        int count = 0;
        for( TaxiPot taxiPot : allTaxiPot ) {
            if(taxiPot.getReservations().size() == taxiPot.getAmount()) {
                count = count + 1;
            }
        }
        return count;
    }

    @Override
    public List<TaxiPotListContentResponse> getTaxiPotList(int size, int page) {
        return taxiPotRepository.findAll(PageRequest.of(page, size))
                .stream().map(TaxiPotListContentResponse::from).collect(Collectors.toList());
    }
    @Override
    public List<TaxiPotListContentResponse> getTaxiPotList(User user, int size, int page) {

        TaxiPotTarget target = getTarget(user.getGcn());

        List<TaxiPot> responses = taxiPotRepository
                .findAllUsersTaxiPot(user.getLatitude(), user.getLongitude(), target);//.subList(size*page, size*page+size)

        if(responses.size()<size*page) {
            return Collections.emptyList();
        } else if(responses.size()<size*page+size) {
            return responses.subList(size*page, responses.size())
                    .stream().map(TaxiPotListContentResponse::from).collect(Collectors.toList());
        }
        return responses.subList(size*page, size*page+size)
                .stream().map(TaxiPotListContentResponse::from).collect(Collectors.toList());
    }

    private TaxiPotTarget getTarget(int gcn){
        if(String.valueOf(gcn).startsWith("1")){
            return TaxiPotTarget.FRESHMAN;
        }
        else if(String.valueOf(gcn).startsWith("2")){
            return TaxiPotTarget.SOPHOMORE;
        }
        else if(String.valueOf(gcn).startsWith("3")){
            return TaxiPotTarget.SENIOR;
        }
        return TaxiPotTarget.ALL;
    }

    @Override
    public TaxiPotContentResponse getTaxiPotContent(int id) {
        TaxiPot taxiPot = taxiPotRepository.getById(id);
        return TaxiPotContentResponse.from(taxiPot);
    }

    @Override
    @Transactional
    public void makeTaxiPot(User user,TaxiPotContentRequest request) {
        taxiPotCheckProvider.checkAlreadyApply(user);

        TaxiPot taxiPot = TaxiPot.builder()
                .creator(user)
                .price(request.getPrice())
                .target(TaxiPotTarget.valueOf(request.getTarget()))
                .createdAt(LocalDateTime.now())
                .meetingAt(request.getMeetingAt())
                .place(request.getPlace())
                .content(request.getContent())
                .destinationLatitude(request.getLatitude())
                .destinationLongitude(request.getLongitude())
                .amount(request.getAmount())
                .build();
        taxiPotRepository.save(taxiPot);

        TaxiPot newTaxiPot = taxiPotRefreshFacade.refreshTaxiPot(taxiPot);

        Reservation reservation = Reservation.builder()
                .id(new ReservationId(taxiPot.getId(), user.getGcn()))
                .user(user)
                .taxiPot(newTaxiPot)
                .build();
        reservationRepository.save(reservation);

        taxiPot.setReservations(reservationRepository.findByIdPotId(taxiPot.getId()));
        taxiPotRepository.save(taxiPot);

        Reservation newReservation = taxiPotRefreshFacade.refreshReservation(reservation);

        user.setReservation(newReservation);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void changeTaxiPotContent(User user, TaxiPotContentRequest request, int id) {
        taxiPotCheckProvider.checkIsCreator(user, id);
        taxiPotCheckProvider.checkChangePossible(id);

        TaxiPot taxiPot = TaxiPot.builder()
                .id(id)
                .creator(user)
                .price(request.getPrice())
                .target(TaxiPotTarget.valueOf(request.getTarget()))
                .createdAt(LocalDateTime.now())
                .meetingAt(request.getMeetingAt())
                .place(request.getPlace())
                .content(request.getContent())
                .destinationLatitude(request.getLatitude())
                .destinationLongitude(request.getLongitude())
                .amount(request.getAmount())
                .build();
        taxiPotRepository.save(taxiPot);
        TaxiPot newTaxiPot = taxiPotRefreshFacade.refreshTaxiPot(taxiPot);

        Reservation reservation = reservationRepository.findById(new ReservationId(id, user.getGcn()))
                .orElseThrow(()-> { throw new ApplyNotFoundException(user.getUsername()); });

        reservation.setTaxiPot(newTaxiPot);
        reservationRepository.save(reservation);

        Reservation newReservation = taxiPotRefreshFacade.refreshReservation(reservation);

        taxiPot.setReservations(reservationRepository.findByIdPotId(taxiPot.getId()));
        taxiPotRepository.save(taxiPot);

        user.setReservation(newReservation);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteTaxiPot(User user, int id) {
        taxiPotCheckProvider.checkIsCreator(user, id);
        taxiPotCheckProvider.checkChangePossible(id);

        reservationRepository.deleteById(new ReservationId(id, user.getGcn()));
        taxiPotRepository.deleteById(id);

        user.setReservation(null);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void applyTaxiPot(User user, int id) {
        taxiPotCheckProvider.checkAlreadyApply(user);
        TaxiPot taxiPot = taxiPotCheckProvider.getTaxiPot(id);
        taxiPotCheckProvider.checkTaxiPotFinishedReservation(taxiPot);

        Reservation reservation = Reservation.builder()
                .id(new ReservationId(taxiPot.getId(), user.getGcn()))
                .user(user)
                .taxiPot(taxiPot)
                .build();
        reservationRepository.save(reservation);
        Reservation newReservation = taxiPotRefreshFacade.refreshReservation(reservation);

        taxiPot.setReservations(reservationRepository.findByIdPotId(id));
        taxiPotRepository.save(taxiPot);

        user.setReservation(newReservation);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void cancelApplyTaxiPot(User user, int id) {
        TaxiPot taxiPot = taxiPotCheckProvider.getTaxiPot(id);
        Reservation reservation = taxiPotCheckProvider.getReservation(user, id);

        reservationRepository.deleteById(new ReservationId(id, user.getGcn()));
        taxiPot.setReservations(reservationRepository.findByIdPotId(id));
        taxiPotRepository.save(taxiPot);

        user.setReservation(null);
        userRepository.save(user);
    }
}
