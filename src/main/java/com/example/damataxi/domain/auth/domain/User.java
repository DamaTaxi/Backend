package com.example.damataxi.domain.auth.domain;

import com.example.damataxi.domain.taxiPot.domain.Reservation;
import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "email", length = 45, nullable = false)
    private String email;

    @Column(name = "gcn")
    private String gcn;

    @Column(name = "username", length = 10, nullable = false)
    private String username;

    @Setter
    @Column(name = "tel", length = 11, nullable = true)
    private String tel;

    @Setter
    @Column(name = "latitude", nullable = true)
    private double latitude;

    @Setter
    @Column(name = "longitude", nullable = true)
    private double longitude;

    @Setter
    @OneToOne(mappedBy = "user")
    private Reservation reservation;
}
