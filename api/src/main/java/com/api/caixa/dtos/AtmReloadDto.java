package com.api.caixa.dtos;

public record AtmReloadDto(
    int notas2,
    int notas5,
    int notas10,
    int notas20,
    int notas50,
    int notas100,
    int notas200
) {}
