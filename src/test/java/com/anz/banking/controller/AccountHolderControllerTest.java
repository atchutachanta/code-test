package com.anz.banking.controller;

import com.anz.banking.entity.Account;
import com.anz.banking.entity.AccountHolder;
import com.anz.banking.repository.AccountHolderRepository;
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
class AccountHolderControllerTest {

    @InjectMocks
    AccountHolderController accountHolderController;
    @Mock
    private AccountHolderRepository accountHolderRepository;

    @Test
    void getAccountHolders() {
        Mockito.when(accountHolderRepository.findAll()).thenReturn(EnhancedRandom.randomListOf(10, AccountHolder.class));

        ResponseEntity<Iterable<AccountHolder>> response = accountHolderController.getAccountHolders();
        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void getAccountHolderById() {
        Mockito.when(accountHolderRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.ofNullable(EnhancedRandom.random(AccountHolder.class)));
        ResponseEntity<AccountHolder> response = accountHolderController.getAccountHolder(1L);
        Assertions.assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void createAccountHolder() {
        AccountHolder accountHolder = EnhancedRandom.random(AccountHolder.class);
        Mockito.when(accountHolderRepository.save(Mockito.any(AccountHolder.class))).thenReturn(accountHolder);
        ResponseEntity<AccountHolder> response = accountHolderController.createAccountHolder(accountHolder);
        Assertions.assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void updateAccount() {
        AccountHolder accountHolder = EnhancedRandom.random(AccountHolder.class);
        Mockito.when(accountHolderRepository.save(Mockito.any(AccountHolder.class))).thenReturn(accountHolder);
        ResponseEntity<AccountHolder> response = accountHolderController.updateAccountHolder(accountHolder);
        Assertions.assertEquals(201, response.getStatusCodeValue());
    }
}
