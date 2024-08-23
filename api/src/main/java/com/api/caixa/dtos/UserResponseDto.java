package com.api.caixa.dtos;

public record UserResponseDto(
    String nome,
    AccountResponseDto conta
) {}
