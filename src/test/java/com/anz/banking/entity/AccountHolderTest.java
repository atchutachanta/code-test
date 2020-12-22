package com.anz.banking.entity;

import com.anz.banking.repository.AccountHolderRepository;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@RunWith(JUnitPlatform.class)
@DataJpaTest
class AccountHolderTest {

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Test
    void createAccountHolder() {
        AccountHolder accountHolder = EnhancedRandom.random(AccountHolder.class,"accounts","id");
        AccountHolder result = accountHolderRepository.save(accountHolder);
        Assertions.assertEquals(accountHolder.getAccountName(), result.getAccountName());
        Assertions.assertNotNull(result.getId());
    }
    @Test
    void updateAccountHolder() {
        AccountHolder accountHolder = EnhancedRandom.random(AccountHolder.class,"accounts","id");
        AccountHolder result = accountHolderRepository.save(accountHolder);
        result.setAccountName("Modified");
        AccountHolder modified = accountHolderRepository.save(result);
        Assertions.assertEquals("Modified", modified.getAccountName());
        Assertions.assertEquals(result.getId(),modified.getId());
    }
    @Test
    void getAccountHolderById() {
        AccountHolder accountHolder = EnhancedRandom.random(AccountHolder.class,"accounts","id");
        AccountHolder result = accountHolderRepository.save(accountHolder);

        Optional<AccountHolder> resultByAccountHolderId = accountHolderRepository.findById(result.getId());
        Long accountHolderID = null;
        if(resultByAccountHolderId.isPresent())
            accountHolderID = resultByAccountHolderId.get().getId();

        Assertions.assertEquals(result.getId(),accountHolderID);
    }
}