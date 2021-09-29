package com.example.damataxi.domain.taxiPot.domain;

import com.example.damataxi.domain.auth.domain.User;
import lombok.*;

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

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId("userEmail")
    @JoinColumn(name = "user_email")
    private User user;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("potId")
    @JoinColumn(name = "pot_id")
    private TaxiPot taxiPot;
}
