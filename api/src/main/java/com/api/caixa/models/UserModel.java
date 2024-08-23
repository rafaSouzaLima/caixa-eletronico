package com.api.caixa.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "tb_users")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; 

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String senha;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL)
    private AccountModel conta;
}
