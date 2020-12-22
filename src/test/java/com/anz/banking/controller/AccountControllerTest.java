package com.anz.banking.controller;

import com.anz.banking.entity.Account;
import com.anz.banking.repository.AccountRepository;
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
class AccountControllerTest {

    @InjectMocks
    AccountController accountController;
    @Mock
    private AccountRepository accountRepository;

    @Test
    void getAccounts() {
        Mockito.when(accountRepository.findAll()).thenReturn(EnhancedRandom.randomListOf(10, Account.class));

        ResponseEntity<Iterable<Account>> response = accountController.getAccounts();
        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getAccountById() {
        Mockito.when(accountRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.ofNullable(EnhancedRandom.random(Account.class)));
        ResponseEntity<Account> response = accountController.getAccountById(1L);
        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void createAccount() {
        Account account = EnhancedRandom.random(Account.class);
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);
        ResponseEntity<Account> response = accountController.createAccount(account);
        Assertions.assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void updateAccount() {
        Account account = EnhancedRandom.random(Account.class);
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);
        ResponseEntity<Account> response = accountController.updateAccount(account);
        Assertions.assertEquals(201, response.getStatusCodeValue());
    }
}
