package com.api.caixa.validators;

import com.api.caixa.dtos.UserRecordDto;

public class NameValidator implements UserValidator {
    private UserValidator proximo;

    @Override
    public void setNext(UserValidator proximo) {
        this.proximo = proximo;
    }

    @Override
    public void validate(UserRecordDto usuario) {
        if(usuario.nome() == null) {
            throw new IllegalArgumentException("O nome é obrigatório");
        }
        if (usuario.nome().matches(".*\\d.*")) {
            throw new IllegalArgumentException("O nome não pode ter números.");
        }

        if (proximo != null) {
            proximo.validate(usuario);
        }
    }
}
