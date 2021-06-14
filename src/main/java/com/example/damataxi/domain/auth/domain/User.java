package com.example.damataxi.domain.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @Column(name = "gcn", nullable = false)
    private Integer gcn;

    @Column(name = "username", length = 10, nullable = false)
    private String username;

    @Column(name = "email", length = 45, nullable = false)
    private String email;

    @Column(name = "tel", length = 11, nullable = true)
    private String tel;

    @Column(name = "latitude", nullable = true)
    private double latitude;

    @Column(name = "longitude", nullable = true)
    private double longitude;

    @Column(name = "reserved_pot", nullable = true)
    private Integer reservedPot;
}
