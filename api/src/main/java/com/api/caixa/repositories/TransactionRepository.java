package com.api.caixa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.api.caixa.models.TransactionModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionModel, Integer> {
    List<TransactionModel> findByContaNumero(String numero);

    List<TransactionModel> findByDataBetween(LocalDateTime startDate, LocalDateTime endDate);

    @Query("SELECT SUM(t.valor) FROM TransactionModel t WHERE t.conta.numero = :numero")
    BigDecimal sumValorByContaNumero(@Param("numero") String numero);
}
