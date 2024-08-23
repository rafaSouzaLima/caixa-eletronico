package com.api.caixa.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_transactions")
@Getter
@Setter
public class TransactionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "conta_id", nullable = false)
    @JsonBackReference//evitando recursão infinita no retorno
    private AccountModel conta;

    @Column(nullable = false)
    private String tipoMovimentacao;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private BigDecimal saldoFinal;

    @Column(nullable = false)
    private LocalDateTime data;

    
    public TransactionModel() {
        this.data = LocalDateTime.now();
    }

    
    public TransactionModel(AccountModel conta, String tipoMovimentacao, BigDecimal valor, BigDecimal saldoFinal) {
        this.conta = conta;
        this.tipoMovimentacao = tipoMovimentacao;
        this.valor = valor;
        this.saldoFinal = saldoFinal;
        this.data = LocalDateTime.now();
    }
}
