package com.api.caixa.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.caixa.dtos.AtmReloadDto;
import com.api.caixa.services.AtmService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/atm")
@CrossOrigin(origins = "http://localhost:5173")
public class AtmController {

    @PostMapping("/recarregar")
    public ResponseEntity<String> reload(
        @RequestBody AtmReloadDto atmReloadDto,
        @RequestParam String nome,
        @RequestParam String senha) {
        
        boolean isValidUser = nome.equals("admin") && senha.equals("admin");

        if (isValidUser) {
            AtmService.getInstance(atmReloadDto);
            return ResponseEntity.ok("Notas adicionadas com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso não autorizado!");
        }
    }
    
}
