package com.example.damataxi.domain.taxiPot;

import com.example.damataxi.IntegrationTest;
import com.example.damataxi.domain.auth.domain.User;
import com.example.damataxi.domain.auth.domain.UserRepository;
import com.example.damataxi.domain.taxiPot.domain.*;
import com.example.damataxi.domain.taxiPot.service.impl.TaxiPotCheckProvider;
import com.example.damataxi.global.error.exception.AlreadyApplyException;
import com.example.damataxi.global.error.exception.NotCreatorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class TaxiPotCheckProviderTest extends IntegrationTest {

    @Autowired
    private TaxiPotCheckProvider taxiPotCheckProvider;
    @Autowired
    private TaxiPotRepository taxiPotRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void clear() {
        reservationRepository.deleteAll();
        taxiPotRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void checkAlreadyApply_테스트() {
        // given
        User user = makeUser(1234);
        // when, then
        taxiPotCheckProvider.checkAlreadyApply(user);
    }

    @Test
    public void checkAlreadyApply_AlreadyApplyException_테스트() {
        // given
        User user = makeUser(1234);
        TaxiPot taxiPot = makeTaxiPot(userRepository.findById(1234).get());
        Reservation reservation = makeReservation(taxiPot, user);

        taxiPot.setReservations(reservationRepository.findByIdPotId(taxiPot.getId()));
        taxiPotRepository.save(taxiPot);

        user.setReservation(reservation);
        userRepository.save(user);
        // when, then
        assertThrows(AlreadyApplyException.class, ()-> taxiPotCheckProvider.checkAlreadyApply(user));
    }

    @Test
    public void checkIsCreator_테스트() {
        // given
        User user = makeUser(1234);
        TaxiPot taxiPot = makeTaxiPot(user);
        // when, then
        taxiPotCheckProvider.checkIsCreator(user, taxiPot.getId());
    }

    @Test
    public void checkIsCreator_NotCreatorException_테스트() {
        // given
        User user = makeUser(1234);
        User fakeUser = makeUser(2345);
        TaxiPot taxiPot = makeTaxiPot(fakeUser);
        // when, then
        assertThrows(NotCreatorException.class, ()-> taxiPotCheckProvider.checkIsCreator(user, taxiPot.getId()));
    }

    @Test
    public void checkChangePossible_테스트() {
        //given
        User user = makeUser(1234);
        TaxiPot taxiPot = makeTaxiPot(user);
        makeReservation(taxiPot, user);

        taxiPot.setReservations(reservationRepository.findByIdPotId(taxiPot.getId()));
        taxiPotRepository.save(taxiPot);

        //when, then
        taxiPotCheckProvider.checkChangePossible(taxiPot.getId());
    }

    private User makeUser(int gcn){
        User user = User.builder()
            .gcn(gcn)
            .username("testUser")
            .email("xxxxxxxx@gmail.com")
            .build();
        userRepository.save(user);
        return userRepository.findById(user.getGcn()).get();
    }

    private TaxiPot makeTaxiPot(User user){
        TaxiPot taxiPot = TaxiPot.builder()
                .creator(user)
                .price(1000)
                .target(TaxiPotTarget.ALL)
                .createdAt(LocalDateTime.now())
                .meetingAt(LocalDateTime.now())
                .place("대마고")
                .destinationLatitude(12.1234)
                .destinationLongitude(34.3456)
                .amount(4)
                .build();
        taxiPotRepository.save(taxiPot);
        return taxiPotRepository.findById(taxiPot.getId()).get();
    }

    private Reservation makeReservation(TaxiPot taxiPot, User user){
        Reservation reservation = Reservation.builder()
                .id(new ReservationId(taxiPot.getId(),user.getGcn()))
                .taxiPot(taxiPot)
                .user(user)
                .build();
        reservationRepository.save(reservation);
        return reservationRepository.findById(reservation.getId()).get();
    }
}
