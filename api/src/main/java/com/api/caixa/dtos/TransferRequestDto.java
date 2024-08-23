package com.api.caixa.dtos;

import java.math.BigDecimal;

public record TransferRequestDto(
    String numeroOrigem,
    String numeroDestino,
    BigDecimal valor
) {}
