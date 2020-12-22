package com.anz.banking.controller;

import com.anz.banking.entity.Account;
import com.anz.banking.entity.Transaction;
import com.anz.banking.repository.TransactionRepository;
import com.anz.banking.service.TransactionService;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class TransactionControllerTest {

    @InjectMocks
    TransactionController transactionController;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private TransactionService transactionService;

    @Test
    void getTransactions() {
        Mockito.when(transactionRepository.findAll()).thenReturn(EnhancedRandom.randomListOf(10, Transaction.class));

        ResponseEntity<Iterable<Transaction>> response = transactionController.getTransactions();
        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getTransactionById() {
        Mockito.when(transactionRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.ofNullable(EnhancedRandom.random(Transaction.class)));
        ResponseEntity<Transaction> response = transactionController.getTransactionsById(1L);
        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void createTransaction() {
        Transaction transaction = EnhancedRandom.random(Transaction.class);
        Mockito.when(transactionService.AddTransaction(Mockito.any(Transaction.class))).thenReturn(transaction);
        ResponseEntity<Transaction> response = transactionController.createTransaction(transaction);
        Assertions.assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void deleteTransaction() {
        Transaction transaction = EnhancedRandom.random(Transaction.class);
        Mockito.when(transactionService.DeleteTransaction(Mockito.anyLong())).thenReturn(transaction);
        ResponseEntity<Transaction> response = transactionController.deleteTransaction(1L);
        Assertions.assertEquals(200, response.getStatusCodeValue());
    }
}
