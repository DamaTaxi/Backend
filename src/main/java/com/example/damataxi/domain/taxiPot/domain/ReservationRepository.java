package com.example.damataxi.domain.taxiPot.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, ReservationId> {
    List<Reservation> findByIdPotId(int id);
    Optional<Reservation> findByIdUserGcn(int id);
}
