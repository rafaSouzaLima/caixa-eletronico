package com.api.caixa.validators;

import com.api.caixa.dtos.UserRecordDto;

public interface UserValidator {
    void setNext(UserValidator proximo);
    void validate(UserRecordDto usuario);
}
