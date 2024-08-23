package com.api.caixa.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tb_atm")
public class AtmModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer notas2;

    @Column(nullable = false)
    private Integer notas5;

    @Column(nullable = false)
    private Integer notas10;

    @Column(nullable = false)
    private Integer notas20;

    @Column(nullable = false)
    private Integer notas50;

    @Column(nullable = false)
    private Integer notas100;

    @Column(nullable = false)
    private Integer notas200;
}
