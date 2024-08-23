package com.api.caixa.dtos;

import java.math.BigDecimal;

public record AccountResponseDto(
    String numero,
    BigDecimal saldo
) {}
