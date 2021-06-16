package com.example.damataxi.domain.auth.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
public class User {

    @Id
    @Column(name = "gcn")
    private Integer gcn;

    @Column(name = "username", length = 10, nullable = false)
    private String username;

    @Column(name = "email", length = 45, nullable = false)
    private String email;

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
    @Column(name = "reserved_pot", nullable = true)
    private Integer reservedPot;
}
