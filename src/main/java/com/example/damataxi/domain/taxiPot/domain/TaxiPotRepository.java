package com.example.damataxi.domain.taxiPot.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaxiPotRepository extends JpaRepository<TaxiPot, Integer> {
    @Query(value = "SELECT u " +
            "FROM TaxiPot As u " +
            "WHERE u.target = 'ALL' OR u.target = :target " +
            "ORDER BY (u.destinationLatitude-:latitude)*(u.destinationLatitude-:latitude) + " +
            "(u.destinationLongitude-:longitude)*(u.destinationLongitude-:longitude)")
    List<TaxiPot> findAllUsersTaxiPot(double latitude, double longitude, TaxiPotTarget target);
    Page<TaxiPot> findAll(Pageable pageable);
}
