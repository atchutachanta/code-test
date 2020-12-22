package com.anz.banking.controller;

import com.anz.banking.entity.Transaction;
import com.anz.banking.repository.TransactionRepository;
import com.anz.banking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionService transactionService;
    @GetMapping(value = "/transactions")
    public ResponseEntity<Iterable<Transaction>> getTransactions() {
        Iterable<Transaction> result = transactionRepository.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/transaction/{id}")
    public ResponseEntity<Transaction> getTransactionsById(@PathVariable Long id) {
        Optional<Transaction> result = transactionRepository.findById(id);
        return result.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/transaction")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction result = transactionService.AddTransaction(transaction);
        return ResponseEntity.created(URI.create("transaction/" + result.getId())).body(result);
    }

    @DeleteMapping(value = "/transaction/{id}")
    public ResponseEntity<Transaction> deleteTransaction(@PathVariable Long id) {
        Transaction result = transactionService.DeleteTransaction(id);
        return ResponseEntity.ok().build();
    }
}

