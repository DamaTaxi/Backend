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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "creator_email")
    private User creator;

    @Convert(converter = TaxiPotTargetConverter.class)
    @Enumerated(EnumType.STRING)
    @Column(name = "target", nullable = false)
    private TaxiPotTarget target;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "meeting_at", nullable = false)
    private LocalDateTime meetingAt;

    @Column(name = "place", length = 45, nullable = false)
    private String place;

    @Column(name = "content", length = 100)
    private String content;

    @Column(name = "destination_latitude", nullable = false)
    private double destinationLatitude;

    @Column(name = "destination_longitude", nullable = false)
    private double destinationLongitude;

    @Column(name = "amount", nullable = false)
    private int amount;

    @Column(name = "title", length = 45, nullable = false)
    private String title;

    @Column(name = "address", length = 45, nullable = false)
    private String address;

    @Setter
    @OneToMany(mappedBy = "taxiPot")
    private List<Reservation> reservations;

}
