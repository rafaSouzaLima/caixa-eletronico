package com.api.caixa.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.caixa.models.AccountModel;
import com.api.caixa.models.TransactionModel;
import com.api.caixa.repositories.AccountRepository;
import com.api.caixa.repositories.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public void transfer(String numeroOrigem, String numeroDestino, BigDecimal valor) {
        AccountModel accountOrigem = accountRepository.findByNumero(numeroOrigem);
        AccountModel accountDestino = accountRepository.findByNumero(numeroDestino);

        if (accountOrigem == null || accountDestino == null) {
            throw new IllegalArgumentException("Uma ou ambas as contas não foram encontradas");
        }

        if(accountOrigem.getNumero() == accountDestino.getNumero()) {
            throw new IllegalArgumentException("Não é possível transferir para a mesma conta");
        }

        if(valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Não é possível transferir valores não positivos");
        }

        accountOrigem.sacar(valor);
        accountDestino.depositar(valor);

        TransactionModel transactionOrigem = new TransactionModel(accountOrigem, "TRANSFERENCIA-SAIDA", valor, accountOrigem.getSaldo());
        TransactionModel transactionDestino = new TransactionModel(accountDestino, "TRANSFERENCIA-ENTRADA", valor, accountDestino.getSaldo());

        transactionRepository.save(transactionOrigem);
        transactionRepository.save(transactionDestino);

        accountRepository.save(accountOrigem);
        accountRepository.save(accountDestino);
    }

    @Transactional
    public void withdraw(String numero, BigDecimal valor) {
        AccountModel account = accountRepository.findByNumero(numero);
        if (account == null) {
            throw new IllegalArgumentException("Conta não encontrada");
        }

        if(! AtmService.withdraw(valor)) {
            throw new IllegalArgumentException("Não há notas suficientes para o seu saque");
        }
        account.sacar(valor);

        TransactionModel transaction = new TransactionModel(account, "SAQUE", valor, account.getSaldo());
        transactionRepository.save(transaction);
        accountRepository.save(account);
    }

    @Transactional
    public void deposit(String numero, BigDecimal valor) {
        AccountModel account = accountRepository.findByNumero(numero);
        if (account == null) {
            throw new IllegalArgumentException("Conta não encontrada");
        }

        account.depositar(valor);

        TransactionModel transaction = new TransactionModel(account, "DEPOSITO", valor, account.getSaldo());
        transactionRepository.save(transaction);
        accountRepository.save(account);
    }

    public List<TransactionModel> extract(String numero) {
        return transactionRepository.findByContaNumero(numero);
    }
}
