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
    private Integer gcn;

    @Column(name = "username", length = 10)
    private String username;

    @Column(name = "email", length = 45)
    private String email;

    @Column(name = "tel", length = 11)
    private String tel;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "reserved_pot")
    private Integer reservedPot;
}
