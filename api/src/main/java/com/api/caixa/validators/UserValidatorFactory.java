package com.api.caixa.validators;

public class UserValidatorFactory {

    public static UserValidator createValidatorChain() {
        // Criação dos validadores
        UserValidator nomeValidador = new NameValidator();
        UserValidator senhaValidador = new SenhaValidator();

        // Configuração da cadeia de responsabilidade
        nomeValidador.setNext(senhaValidador);

        // Retorna o primeiro validador na cadeia
        return nomeValidador;
    }
}

