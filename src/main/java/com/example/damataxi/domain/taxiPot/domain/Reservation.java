package com.example.damataxi.domain.taxiPot.domain;

import com.example.damataxi.domain.auth.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "reservation")
public class Reservation {
    @EmbeddedId
    private ReservationId id;

    @OneToOne(mappedBy = "reservation")
    @MapsId("userGcn")
    @JoinColumn(name = "gcn")
    private User user;

    @ManyToOne
    @MapsId("potId")
    @JoinColumn(name = "id")
    private TaxiPot taxiPot;
}
