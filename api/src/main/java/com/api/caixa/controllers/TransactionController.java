package com.api.caixa.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.caixa.dtos.DepositRequestDto;
import com.api.caixa.dtos.TransferRequestDto;
import com.api.caixa.dtos.WithdrawRequestDto;
import com.api.caixa.models.TransactionModel;
import com.api.caixa.services.TransactionService;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://localhost:5173")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transferencia")
    public ResponseEntity<Map<String, String>> transfer(@RequestBody TransferRequestDto transferRequestDto) {
        Map<String, String> response = new HashMap<>();
        try {
            transactionService.transfer(
                    transferRequestDto.numeroOrigem(),
                    transferRequestDto.numeroDestino(),
                    transferRequestDto.valor());
            response.put("message", "Transferência realizada com sucesso");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/saque")
    public ResponseEntity<Map<String, String>> withdraw(@RequestBody WithdrawRequestDto withdrawRequestDto) {
        Map<String, String> response = new HashMap<>();
        try {
            transactionService.withdraw(withdrawRequestDto.numero(), withdrawRequestDto.valor());
            response.put("message", "Saque realizado com sucesso");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/deposito")
    public ResponseEntity<Map<String, String>> deposit(@RequestBody DepositRequestDto depositRequestDto) {
        Map<String, String> response = new HashMap<>();
        try {
            transactionService.deposit(depositRequestDto.numero(), depositRequestDto.valor());
            response.put("message", "Depósito realizado com sucesso");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e) {
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }// agora retorna um json valido

    @GetMapping("/extrato/{numero}")
    public ResponseEntity<List<TransactionModel>> extract(@PathVariable String numero) {
        List<TransactionModel> transactions = transactionService.extract(numero);
        return ResponseEntity.ok(transactions);
    }
}
