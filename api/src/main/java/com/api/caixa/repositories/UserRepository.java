package com.api.caixa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.caixa.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Integer> {
    boolean existsByNome(String nome);

    UserModel findByNome(String nome);
}
