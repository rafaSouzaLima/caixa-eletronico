package com.api.caixa.services;

import java.math.BigDecimal;

import com.api.caixa.dtos.AtmReloadDto;

import lombok.Getter;

public class AtmService {
    private static AtmService instancia;

    private AtmService() {}

    @Getter
    private static int notas2 = 0;
    @Getter
    private static int notas5 = 0;
    @Getter
    private static int notas10 = 0;
    @Getter
    private static int notas20 = 0;
    @Getter
    private static int notas50 = 0;
    @Getter
    private static int notas100 = 0;
    @Getter
    private static int notas200 = 0;

    public static synchronized AtmService getInstance() {
        if(instancia == null) {
            instancia = new AtmService();
        }

        return instancia;
    }

    public static synchronized AtmService getInstance(
        AtmReloadDto aditionalData
    ) {
        AtmService instancia = AtmService.getInstance();
        
        notas2 += aditionalData.notas2();
        notas5 += aditionalData.notas5();
        notas10 += aditionalData.notas10();
        notas20 += aditionalData.notas20();
        notas50 += aditionalData.notas50();
        notas100 += aditionalData.notas100();
        notas200 += aditionalData.notas200();

        return instancia;
    }


    public static synchronized boolean withdraw(BigDecimal valor) {
        AtmService.getInstance();
        
        int valorInteiro = valor.intValue();

        int quantidade200 = 0, quantidade100 = 0, quantidade50 = 0, quantidade20 = 0, quantidade10 = 0, quantidade5 = 0, quantidade2 = 0;

        quantidade200 = valorInteiro / 200;
        int restante = valorInteiro % 200;
        
        if(restante != 0) {
            quantidade100 = restante / 100;
            restante = restante % 100;
        }

        if(restante != 0) { 
            quantidade50 = restante / 50;
            restante = restante % 50;
        }

        if(restante != 0){ 
            quantidade20 = restante / 20;
            restante = restante % 20;
        }

        if(restante != 0) {
            quantidade10 = restante / 10;
            restante = restante % 10;
        }

        if(restante != 0) {
            quantidade5 = restante / 5;
            restante = restante % 5;
        }

        if(restante != 0) { 
            quantidade2 = restante / 2; 
            restante = restante % 2;
        }

        // Há notas suficientes
        if (quantidade200 > notas200 ||
            quantidade100 > notas100 ||
            quantidade50 > notas50 ||
            quantidade20 > notas20 ||
            quantidade10 > notas10 ||
            quantidade5 > notas5 ||
            quantidade2 > notas2) {
            return false;
        }

        notas200 -= quantidade200;
        notas100 -= quantidade100;
        notas50 -= quantidade50;
        notas20 -= quantidade20;
        notas10 -= quantidade10;
        notas5 -= quantidade5;
        notas2 -= quantidade2;

        return true;
    }
}
