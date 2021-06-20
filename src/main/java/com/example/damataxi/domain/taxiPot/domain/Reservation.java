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

    @OneToOne
    @MapsId("userGcn")
    @JoinColumn(name = "user_gcn")
    private User user;

    @Setter
    @ManyToOne
    @MapsId("potId")
    @JoinColumn(name = "pot_id")
    private TaxiPot taxiPot;
}
