package com.anz.banking.entity;

import com.anz.banking.repository.TransactionRepository;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(JUnitPlatform.class)
@DataJpaTest
class TransactionTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Test
    void createTransaction() {
        Transaction transaction = EnhancedRandom.random(Transaction.class,"id");
        Transaction result = transactionRepository.save(transaction);
        Assertions.assertEquals(transaction.getTransactionType(), result.getTransactionType());
        Assertions.assertEquals(transaction.getCurrency(), result.getCurrency());
        Assertions.assertEquals(transaction.getCreditAmount(), result.getCreditAmount());
        Assertions.assertNotNull(result.getId());
    }
    @Test
    void updateTransaction() {
        Transaction transaction = EnhancedRandom.random(Transaction.class,"id");
        Transaction result = transactionRepository.save(transaction);
        result.setTransactionType(Transaction.TransactionType.DEBIT);
        result.setCurrency(Transaction.Currency.AUD);
        result.setCreditAmount(BigDecimal.valueOf(1000L));
        Transaction modified = transactionRepository.save(result);
        Assertions.assertEquals(Transaction.TransactionType.DEBIT, modified.getTransactionType());
        Assertions.assertEquals(Transaction.Currency.AUD, modified.getCurrency());
        Assertions.assertEquals(result.getId(),modified.getId());
        assertEquals(0, result.getCreditAmount().compareTo(BigDecimal.valueOf(1000)));
    }

    @Test
    void getTransactionById() {
        Transaction transaction = EnhancedRandom.random(Transaction.class,"id");
        Transaction result = transactionRepository.save(transaction);

        Optional<Transaction> resultByTransactionId = transactionRepository.findById(result.getId());
        Long transactionID = null;
        if(resultByTransactionId.isPresent())
            transactionID = resultByTransactionId.get().getId();

        Assertions.assertEquals(result.getId(),transactionID);
    }
}