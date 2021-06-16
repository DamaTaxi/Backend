package com.example.damataxi.domain.taxiPot.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, ReservationId> {
    List<Reservation> findByIdPotId(int id);
}
