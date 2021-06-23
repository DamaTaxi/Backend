package com.example.damataxi.domain.taxiPot.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaxiPotRepository extends JpaRepository<TaxiPot, Integer> {
    Page<TaxiPot> findAll(Pageable pageable);
}
