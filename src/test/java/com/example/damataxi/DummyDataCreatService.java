package com.example.damataxi;

import com.example.damataxi.domain.auth.domain.Admin;
import com.example.damataxi.domain.auth.domain.AdminRepository;
import com.example.damataxi.domain.auth.domain.User;
import com.example.damataxi.domain.auth.domain.UserRepository;
import com.example.damataxi.domain.errorReport.domain.ErrorReport;
import com.example.damataxi.domain.errorReport.domain.ErrorReportRepository;
import com.example.damataxi.domain.suggestion.domain.Suggestion;
import com.example.damataxi.domain.suggestion.domain.SuggestionRepository;
import com.example.damataxi.domain.taxiPot.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DummyDataCreatService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private TaxiPotRepository taxiPotRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ErrorReportRepository errorReportRepository;
    @Autowired
    private SuggestionRepository suggestionRepository;

    public User makeUser(String gcn){
        User user = User.builder()
                .email("xxxx@gmail.com")
                .gcn(gcn)
                .username("testUser")
                .build();
        userRepository.save(user);
        return userRepository.findById(user.getEmail()).get();
    }

    public User makeUser(String gcn, String email){
        User user = User.builder()
                .email(email)
                .gcn(gcn)
                .username("testUser")
                .build();
        userRepository.save(user);
        return userRepository.findById(user.getEmail()).get();
    }

    public TaxiPot makeTaxiPot(User user){
        TaxiPot taxiPot = TaxiPot.builder()
                .creator(user)
                .price(1000)
                .target(TaxiPotTarget.ALL)
                .createdAt(LocalDateTime.now())
                .meetingAt(LocalDateTime.now())
                .place("대마고")
                .destinationLatitude(12.1234)
                .destinationLongitude(34.3456)
                .title("가나다라마바사노래방")
                .amount(4)
                .build();
        taxiPotRepository.save(taxiPot);
        return taxiPotRepository.findById(taxiPot.getId()).get();
    }

    public TaxiPot makeTaxiPot(User user, double latitude, double longtitude){
        TaxiPot taxiPot = TaxiPot.builder()
                .creator(user)
                .price(1000)
                .target(TaxiPotTarget.ALL)
                .createdAt(LocalDateTime.now())
                .meetingAt(LocalDateTime.now())
                .place("대마고")
                .destinationLatitude(latitude)
                .destinationLongitude(longtitude)
                .title("가나다라마바사노래방")
                .amount(4)
                .build();
        taxiPotRepository.save(taxiPot);
        return taxiPotRepository.findById(taxiPot.getId()).get();
    }

    public TaxiPot makeTaxiPot(User user, TaxiPotTarget target){
        TaxiPot taxiPot = TaxiPot.builder()
                .creator(user)
                .price(1000)
                .target(target)
                .createdAt(LocalDateTime.now())
                .meetingAt(LocalDateTime.now())
                .place("대마고")
                .destinationLatitude(12.1234)
                .destinationLongitude(34.3456)
                .title("가나다라마바사노래방")
                .amount(4)
                .build();
        taxiPotRepository.save(taxiPot);
        return taxiPotRepository.findById(taxiPot.getId()).get();
    }

    public Reservation makeReservation(TaxiPot taxiPot, User user){
        Reservation reservation = Reservation.builder()
                .id(new ReservationId(taxiPot.getId(),user.getGcn()))
                .taxiPot(taxiPot)
                .user(user)
                .build();
        reservationRepository.save(reservation);
        Reservation newReservation = reservationRepository.findById(reservation.getId()).get();
        taxiPot.setReservations(reservationRepository.findByIdPotId(taxiPot.getId()));
        taxiPotRepository.save(taxiPot);
        user.setReservation(newReservation);
        userRepository.save(user);
        return newReservation;
    }

    public Admin makeAdmin(String username) {
        Admin admin = Admin.builder()
                .username(username)
                .password("password")
                .build();
        adminRepository.save(admin);
        return adminRepository.findById(admin.getUsername()).get();
    }

    public ErrorReport makeErrorReport() {
        ErrorReport errorReport = ErrorReport.builder()
                .title("testTitle")
                .content("testContent")
                .build();
        errorReportRepository.save(errorReport);
        return errorReportRepository.findById(errorReport.getId()).get();
    }

    public Suggestion makeSuggestion() {
        Suggestion suggestion = Suggestion.builder()
                .title("testTitle")
                .content("testContent")
                .build();
        suggestionRepository.save(suggestion);
        return suggestionRepository.findById(suggestion.getId()).get();
    }
}
