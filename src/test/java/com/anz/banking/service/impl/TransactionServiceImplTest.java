package com.anz.banking.service.impl;

import com.anz.banking.entity.Transaction;
import com.anz.banking.exception.TransactionException;
import com.anz.banking.repository.AccountRepository;
import com.anz.banking.repository.TransactionRepository;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class TransactionServiceImplTest {


    @InjectMocks
    TransactionServiceImpl transactionService;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private TransactionRepository transactionRepository;

    @Test
    void addDebitTransaction() {
        Transaction transaction = EnhancedRandom.random(Transaction.class);
        transaction.setTransactionType(Transaction.TransactionType.DEBIT);
        transaction.setDebitAmount(BigDecimal.valueOf(100));

        transaction.getAccount().setBalance(BigDecimal.valueOf(1000));

        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(transaction);
        Mockito.when(accountRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(transaction.getAccount()));
        Transaction result = transactionService.AddTransaction(transaction);
        assertEquals(0,result.getAccount().getBalance().compareTo(BigDecimal.valueOf(900)));
    }

    @Test
    void addDebitTransactionWithException() {
        Transaction transaction = EnhancedRandom.random(Transaction.class);
        transaction.setTransactionType(Transaction.TransactionType.DEBIT);
        transaction.setDebitAmount(BigDecimal.valueOf(2000));

        transaction.getAccount().setBalance(BigDecimal.valueOf(1000));

        Mockito.when(accountRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(transaction.getAccount()));
        assertThrows(TransactionException.class, () -> transactionService.AddTransaction(transaction));
    }

    @Test
    void addCreditTransaction() {
        Transaction transaction = EnhancedRandom.random(Transaction.class);
        transaction.setTransactionType(Transaction.TransactionType.CREDIT);
        transaction.setCreditAmount(BigDecimal.valueOf(100));

        transaction.getAccount().setBalance(BigDecimal.valueOf(1000));

        Mockito.when(transactionRepository.save(Mockito.any(Transaction.class))).thenReturn(transaction);
        Mockito.when(accountRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(transaction.getAccount()));
        Transaction result = transactionService.AddTransaction(transaction);
        assertEquals(0,result.getAccount().getBalance().compareTo(BigDecimal.valueOf(1100)) );
    }

    @Test
    void deleteDebitTransaction() {
        Transaction transaction = EnhancedRandom.random(Transaction.class);
        transaction.setTransactionType(Transaction.TransactionType.DEBIT);
        transaction.setDebitAmount(BigDecimal.valueOf(100));

        transaction.getAccount().setBalance(BigDecimal.valueOf(1000));

        Mockito.when(transactionRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(transaction));
        Mockito.when(accountRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(transaction.getAccount()));
        Transaction result = transactionService.DeleteTransaction(1L);
        assertEquals(0,result.getAccount().getBalance().compareTo(BigDecimal.valueOf(1100)) );
    }

    @Test
    void deleteCreditTransaction() {
        Transaction transaction = EnhancedRandom.random(Transaction.class);
        transaction.setTransactionType(Transaction.TransactionType.CREDIT);
        transaction.setCreditAmount(BigDecimal.valueOf(100));

        transaction.getAccount().setBalance(BigDecimal.valueOf(1000));

        Mockito.when(transactionRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(transaction));
        Mockito.when(accountRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(transaction.getAccount()));
        Transaction result = transactionService.DeleteTransaction(1L);
        assertEquals(0,result.getAccount().getBalance().compareTo(BigDecimal.valueOf(900)) );
    }
}