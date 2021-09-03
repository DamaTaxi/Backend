package com.example.damataxi.global.querydsl;

import com.example.damataxi.domain.auth.domain.QUser;
import com.example.damataxi.domain.auth.domain.User;
import com.example.damataxi.domain.taxiPot.domain.QReservation;
import com.example.damataxi.domain.taxiPot.domain.QTaxiPot;
import com.example.damataxi.domain.taxiPot.domain.TaxiPot;
import com.example.damataxi.domain.taxiPot.domain.TaxiPotTarget;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class QueryDslRepository {

    private final JPAQueryFactory query;

    public List<TaxiPot> getUsersTaxiPot(double latitude, double longitude, TaxiPotTarget target, int limit, int offset, User user) {
        QTaxiPot qtaxiPot = QTaxiPot.taxiPot;
        QReservation qreservation = QReservation.reservation;
        QUser quser = QUser.user;
        return query.select(qtaxiPot)
                .from(qtaxiPot)
                .join(qtaxiPot.reservations, qreservation)
                .join(qreservation.user, quser).on(quser.ne(user))
                .where((qtaxiPot.target.eq(TaxiPotTarget.ALL).or(qtaxiPot.target.eq(target))))
                .orderBy((qtaxiPot.destinationLatitude.subtract(latitude)).multiply(qtaxiPot.destinationLongitude.subtract(longitude)).asc())
                .offset(offset).limit(limit)
                .fetch();
    }

    public long getUserTaxiPotAmount(double latitude, double longitude, TaxiPotTarget target, int limit, int offset, User user) {
        QTaxiPot qtaxiPot = QTaxiPot.taxiPot;
        QReservation qreservation = QReservation.reservation;
        QUser quser = QUser.user;
        return query
                .from(qtaxiPot)
                .join(qtaxiPot.reservations, qreservation)
                .join(qreservation.user, quser).on(quser.ne(user))
                .where((qtaxiPot.target.eq(TaxiPotTarget.ALL).or(qtaxiPot.target.eq(target))))
                .orderBy((qtaxiPot.destinationLatitude.subtract(latitude)).multiply(qtaxiPot.destinationLongitude.subtract(longitude)).asc())
                .fetchCount();
    }
}
