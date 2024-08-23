package com.api.caixa.dtos;

import java.math.BigDecimal;

public record WithdrawRequestDto(
    String numero,
    BigDecimal valor
) {}