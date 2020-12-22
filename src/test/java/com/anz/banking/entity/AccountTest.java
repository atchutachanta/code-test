package com.anz.banking.entity;

import com.anz.banking.repository.AccountRepository;
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
class AccountTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void createAccount() {
        Account account = EnhancedRandom.random(Account.class,"transactions","accountNumber");
        Account result = accountRepository.save(account);
        Assertions.assertEquals(account.getAccountType(), result.getAccountType());
        Assertions.assertEquals(account.getCurrency(), result.getCurrency());
        Assertions.assertEquals(account.getBalance(), result.getBalance());
        Assertions.assertNotNull(result.getAccountNumber());
    }
    @Test
    void updateAccount() {
        Account account = EnhancedRandom.random(Account.class,"transactions","accountNumber");
        Account result = accountRepository.save(account);
        result.setAccountType(Account.AccountType.SAVINGS);
        result.setCurrency(Account.Currency.SGD);
        result.setBalance(BigDecimal.valueOf(1000L));
        Account modified = accountRepository.save(result);
        Assertions.assertEquals(Account.AccountType.SAVINGS, modified.getAccountType());
        Assertions.assertEquals(Account.Currency.SGD, modified.getCurrency());
        Assertions.assertEquals(result.getAccountNumber(),modified.getAccountNumber());
        assertEquals(0, result.getBalance().compareTo(BigDecimal.valueOf(1000)));
    }

    @Test
    void getAccountById() {
        Account account = EnhancedRandom.random(Account.class,"transactions","accountNumber");
        Account result = accountRepository.save(account);

        Optional<Account> resultByAccountId = accountRepository.findById(result.getAccountNumber());
        Long accountID = null;
        if(resultByAccountId.isPresent())
            accountID = resultByAccountId.get().getAccountNumber();

        Assertions.assertEquals(result.getAccountNumber(),accountID);
    }
}