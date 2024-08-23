package com.api.caixa.dtos;

import java.math.BigDecimal;

public record DepositRequestDto(
    String numero,
    BigDecimal valor
) {}
