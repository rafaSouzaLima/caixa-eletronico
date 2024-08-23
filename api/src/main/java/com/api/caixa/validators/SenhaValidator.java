package com.api.caixa.validators;

import com.api.caixa.dtos.UserRecordDto;

public class SenhaValidator implements UserValidator {
    private UserValidator proximo;

    @Override
    public void setNext(UserValidator proximo) {
        this.proximo = proximo;
    }

    @Override
    public void validate(UserRecordDto usuario) {
        if(usuario.senha() == null) {
            throw new IllegalArgumentException("A senha é obrigatória");
        }
        if (usuario.senha().trim().isEmpty() || usuario.senha().contains(" ")) {
            throw new IllegalArgumentException("A senha não pode ser vazia ou ter espaços em branco");
        }

        if (proximo != null) {
            proximo.validate(usuario);
        }
    }

}
