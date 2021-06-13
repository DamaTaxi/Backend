package com.example.damataxi.domain.auth.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Getter
@Entity
public class Admin {
    @Id
    @Column(name = "username")
    private String username;
}
