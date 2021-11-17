package com.example.damataxi.domain.taxiPot.service.impl;

import com.example.damataxi.domain.auth.domain.User;
import com.example.damataxi.domain.auth.domain.UserRepository;
import com.example.damataxi.domain.taxiPot.domain.*;
import com.example.damataxi.domain.taxiPot.dto.request.TaxiPotContentRequest;
import com.example.damataxi.domain.taxiPot.dto.response.*;
import com.example.damataxi.domain.taxiPot.service.TaxiPotService;
import com.example.damataxi.global.error.exception.ApplyNotFoundException;
import com.example.damataxi.global.error.exception.TaxiPotNotFoundException;
import com.example.damataxi.global.querydsl.QueryDslRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class
TaxiPotServiceImpl implements TaxiPotService {

    private final TaxiPotCheckProvider taxiPotCheckProvider;
    private final TaxiPotRefreshFacade taxiPotRefreshFacade;

    private final UserRepository userRepository;
    private final TaxiPotRepository taxiPotRepository;
    private final ReservationRepository reservationRepository;
    private final QueryDslRepository queryDslRepository;

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
    public List<TaxiPotSlideContentResponse> getTaxiPotSlideList() {
        List<TaxiPot> taxiPots = taxiPotRepository.findAll();
        return taxiPots
                .stream().map(TaxiPotSlideContentResponse::from).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<TaxiPotSlideContentResponse> getTaxiPotSlideList(User user) {
        TaxiPotTarget target = getTarget(user.getGcn());
        List<TaxiPot> taxiPots = queryDslRepository
                .getUsersTaxiPot(user.getLatitude(), user.getLongitude(), target, user);

        return taxiPots
                .stream().map(TaxiPotSlideContentResponse::from).collect(Collectors.toList());
    }

    @Override
    public TaxiPotPage getTaxiPotList(int size, int page) {
        Page<TaxiPot> taxiPots = taxiPotRepository.findAll(PageRequest.of(page, size));
        List<TaxiPotListContentResponse> content = taxiPots
                .stream().map(TaxiPotListContentResponse::from).collect(Collectors.toList());
        return new TaxiPotPage(taxiPots.getTotalElements(), taxiPots.getTotalPages(), content);
    }
    @Override
    public TaxiPotPage getTaxiPotList(User user, int size, int page) {

        TaxiPotTarget target = getTarget(user.getGcn());
        List<TaxiPot> taxiPots = queryDslRepository
                .getUsersTaxiPot(user.getLatitude(), user.getLongitude(), target, size, size*page, user);

        long totalElements = queryDslRepository
                .getUserTaxiPotAmount(user.getLatitude(), user.getLongitude(), target, size, size*page, user);
        int totalPages = (totalElements%size!=0) ? ((int)(totalElements/size)+1) : (int)(totalElements/size);
        List<TaxiPotListContentResponse> content = taxiPots
                .stream().map(TaxiPotListContentResponse::from).collect(Collectors.toList());
        return new TaxiPotPage(totalElements, totalPages, content);
    }

    private TaxiPotTarget getTarget(String gcn){

        if (gcn.startsWith("1")) {
            return TaxiPotTarget.FRESHMAN;
        } else if (gcn.startsWith("2")) {
            return TaxiPotTarget.SOPHOMORE;
        } else if (gcn.startsWith("3")) {
            return TaxiPotTarget.SENIOR;
        }
        return TaxiPotTarget.ALL;

    }

    @Override
    public TaxiPotContentResponse getTaxiPotContent(int id) {
        TaxiPot taxiPot = taxiPotRepository.findById(id)
                .orElseThrow(()-> new TaxiPotNotFoundException(id));
        return TaxiPotContentResponse.from(taxiPot);
    }

    @Override
    @Transactional
    public void makeTaxiPot(User user,TaxiPotContentRequest request) {
        TaxiPotTarget taxiPotTarget = TaxiPotTarget.valueOf(request.getTarget());

        taxiPotCheckProvider.checkAlreadyApply(user);
        taxiPotCheckProvider.checkCorrectTarget(getTarget(user.getGcn()), taxiPotTarget);

        TaxiPot taxiPot = TaxiPot.builder()
                .creator(user)
                .target(taxiPotTarget)
                .createdAt(LocalDateTime.now())
                .meetingAt(request.getMeetingAt())
                .place(request.getPlace())
                .content(request.getContent())
                .destinationLatitude(request.getLatitude())
                .destinationLongitude(request.getLongitude())
                .amount(request.getAmount())
                .title(request.getTitle())
                .address(request.getAddress())
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
        TaxiPotTarget taxiPotTarget = TaxiPotTarget.valueOf(request.getTarget());

        taxiPotCheckProvider.checkIsCreator(user, id);
        taxiPotCheckProvider.checkChangePossible(id);
        taxiPotCheckProvider.checkCorrectTarget(getTarget(user.getGcn()), taxiPotTarget);

        TaxiPot taxiPot = TaxiPot.builder()
                .id(id)
                .creator(user)
                .target(taxiPotTarget)
                .createdAt(LocalDateTime.now())
                .meetingAt(request.getMeetingAt())
                .place(request.getPlace())
                .content(request.getContent())
                .destinationLatitude(request.getLatitude())
                .destinationLongitude(request.getLongitude())
                .title(request.getTitle())
                .amount(request.getAmount())
                .address(request.getAddress())
                .build();
        taxiPotRepository.save(taxiPot);
        TaxiPot newTaxiPot = taxiPotRefreshFacade.refreshTaxiPot(taxiPot);

        Reservation reservation = reservationRepository.findById(new ReservationId(id, user.getEmail()))
                .orElseThrow(()-> new ApplyNotFoundException(user.getUsername()));

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

        reservationRepository.deleteById(new ReservationId(id, user.getEmail()));
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
        taxiPotCheckProvider.checkCorrectTarget(getTarget(user.getGcn()), taxiPot.getTarget());

        Reservation reservation = Reservation.builder()
                .id(new ReservationId(taxiPot.getId(), user.getEmail()))
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
        if (taxiPot.getCreator().getEmail().equals(user.getEmail())) {
            this.deleteTaxiPot(user, id);
            return;
        }
        taxiPotCheckProvider.getReservation(user, id);

        reservationRepository.deleteById(new ReservationId(id, user.getEmail()));
        taxiPot.setReservations(reservationRepository.findByIdPotId(id));
        taxiPotRepository.save(taxiPot);

        user.setReservation(null);
        userRepository.save(user);
    }
}
