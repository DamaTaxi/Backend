package com.example.damataxi.domain.taxiPot.domain;

import com.example.damataxi.domain.auth.domain.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "taxi_pot")
public class TaxiPot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "creator", nullable = false)
    private int creator;

    @Column(name = "price", nullable = false)
    private int price;

    @Column(name = "target", nullable = false)
    private TaxiPotTarget target;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "meeting_at", nullable = false)
    private LocalDateTime meetingAt;

    @Column(name = "place", nullable = false)
    private String place;

    @Column(name = "content", nullable = true)
    private String content;

    @Column(name = "destination_latitude", nullable = false)
    private double destinationLatitude;

    @Column(name = "destination_longitude", nullable = false)
    private double destinationLongitude;

    @Column(name = "all", nullable = false)
    private int all;

    @OneToOne(mappedBy = "taxiPot")
    private User user;

    @Setter
    @OneToMany(mappedBy = "taxiPot")
    private List<Reservation> reservations;
}
