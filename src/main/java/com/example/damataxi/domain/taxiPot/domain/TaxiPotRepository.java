package com.example.damataxi.domain.taxiPot.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxiPotRepository extends JpaRepository<TaxiPot, Integer> {
    Page<TaxiPot> findAll(Pageable pageable);
}
