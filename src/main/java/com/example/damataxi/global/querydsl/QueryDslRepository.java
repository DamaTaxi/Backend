package com.example.damataxi.global.querydsl;

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

    public List<TaxiPot> getUsersTaxiPot(double latitude, double longitude, TaxiPotTarget target, int limit, int offset) {
        QTaxiPot taxiPot = QTaxiPot.taxiPot;
        return query.select(taxiPot)
                .from(taxiPot)
                .where(taxiPot.target.eq(TaxiPotTarget.ALL).or(taxiPot.target.eq(target)))
                .orderBy((taxiPot.destinationLatitude.subtract(latitude)).multiply(taxiPot.destinationLongitude.subtract(longitude)).asc())
                .offset(offset).limit(limit)
                .fetch();
    }
}
