package com.example.damataxi.domain.taxiPot;

import com.example.damataxi.DummyDataCreatService;
import com.example.damataxi.IntegrationTest;
import com.example.damataxi.domain.auth.domain.User;
import com.example.damataxi.domain.auth.domain.UserRepository;
import com.example.damataxi.domain.taxiPot.domain.*;
import com.example.damataxi.domain.taxiPot.service.impl.TaxiPotCheckProvider;
import com.example.damataxi.global.error.exception.AlreadyApplyException;
import com.example.damataxi.global.error.exception.ImpossibleChangeException;
import com.example.damataxi.global.error.exception.InvalidInputValueException;
import com.example.damataxi.global.error.exception.NotCreatorException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
    @Autowired
    private DummyDataCreatService dummyDataCreatService;

    @AfterEach
    public void clear() {
        reservationRepository.deleteAll();
        taxiPotRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void checkAlreadyApply_테스트() {
        // given
        User user = dummyDataCreatService.makeUser(1234);
        // when, then
        taxiPotCheckProvider.checkAlreadyApply(user);
    }

    @Test
    public void checkAlreadyApply_AlreadyApplyException_테스트() {
        // given
        User user = dummyDataCreatService.makeUser(1234);
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(userRepository.findById(1234).get());
        Reservation reservation = dummyDataCreatService.makeReservation(taxiPot, user);

        // when, then
        assertThrows(AlreadyApplyException.class, ()-> taxiPotCheckProvider.checkAlreadyApply(user));
    }

    @Test
    public void checkIsCreator_테스트() {
        // given
        User user = dummyDataCreatService.makeUser(1234);
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(user);
        // when, then
        taxiPotCheckProvider.checkIsCreator(user, taxiPot.getId());
    }

    @Test
    public void checkIsCreator_NotCreatorException_테스트() {
        // given
        User user = dummyDataCreatService.makeUser(1234);
        User fakeUser = dummyDataCreatService.makeUser(2345);
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(fakeUser);
        // when, then
        assertThrows(NotCreatorException.class, ()-> taxiPotCheckProvider.checkIsCreator(user, taxiPot.getId()));
    }

    @Test
    public void checkChangePossible_테스트() {
        // given
        User user = dummyDataCreatService.makeUser(1234);
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(user);
        dummyDataCreatService.makeReservation(taxiPot, user);

        // when, then
        taxiPotCheckProvider.checkChangePossible(taxiPot.getId());
    }

    @Test
    public void checkChangePossible_ImpossibleChangeException_테스트() {
        // given
        User user1 = dummyDataCreatService.makeUser(1234);
        TaxiPot taxiPot = dummyDataCreatService.makeTaxiPot(user1);
        dummyDataCreatService.makeReservation(taxiPot, user1);
        User user2 = dummyDataCreatService.makeUser(2345);
        dummyDataCreatService.makeReservation(taxiPot, user2);

        // when, then
        assertThrows(ImpossibleChangeException.class, ()->taxiPotCheckProvider.checkChangePossible(taxiPot.getId()));
    }

    @Test
    public void checkCorrectTarget_테스트() {
        //when, then
        taxiPotCheckProvider.checkCorrectTarget(TaxiPotTarget.FRESHMAN, TaxiPotTarget.ALL);
        taxiPotCheckProvider.checkCorrectTarget(TaxiPotTarget.FRESHMAN, TaxiPotTarget.FRESHMAN);
    }

    @Test
    public void checkCorrectTarget_InvalidInputValueException_테스트() {
        //when, then
        assertThrows(InvalidInputValueException.class,
                ()->taxiPotCheckProvider.checkCorrectTarget(TaxiPotTarget.FRESHMAN, TaxiPotTarget.SENIOR));
    }
}
