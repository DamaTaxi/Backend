package com.example.damataxi.domain.taxiPot.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ReservationId implements Serializable {
    @Column(name = "pot_id", nullable = false)
    private int potId;

    @Column(name = "user_gcn", nullable = false)
    private int userGcn;
}
