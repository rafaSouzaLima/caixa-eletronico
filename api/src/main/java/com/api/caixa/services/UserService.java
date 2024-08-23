package com.api.caixa.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.caixa.dtos.AccountResponseDto;
import com.api.caixa.dtos.UserRecordDto;
import com.api.caixa.dtos.UserResponseDto;
import com.api.caixa.models.AccountModel;
import com.api.caixa.models.UserModel;
import com.api.caixa.repositories.UserRepository;
import com.api.caixa.validators.UserValidator;
import com.api.caixa.validators.UserValidatorFactory;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponseDto saveUser(UserRecordDto userRecordDto) {
        if (userRepository.existsByNome(userRecordDto.nome())) {
            throw new IllegalArgumentException("Nome já cadastrado");
        }
        UserValidator validator = UserValidatorFactory.createValidatorChain();
        validator.validate(userRecordDto);

        UserModel user = new UserModel();
        user.setNome(userRecordDto.nome());
        user.setSenha(userRecordDto.senha());

        AccountModel account = new AccountModel();

        user.setConta(account);
        account.setCliente(user);

        userRepository.save(user);

        return new UserResponseDto(user.getNome(), new AccountResponseDto(account.getNumero(), account.getSaldo()));
    }

    @Transactional
    public UserModel findUser(String nome, String senha) {
        UserModel user = userRepository.findByNome(nome);
        if (user != null && user.getSenha().equals(senha)) {
            return user;
        }
        return null;
    }
}

