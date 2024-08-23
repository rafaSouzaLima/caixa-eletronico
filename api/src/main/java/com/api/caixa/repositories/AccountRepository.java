package com.api.caixa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.caixa.models.AccountModel;

public interface AccountRepository extends JpaRepository<AccountModel, Integer> {
    AccountModel findByNumero(String numero);
}
