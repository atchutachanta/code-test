package com.anz.banking.controller;

import com.anz.banking.entity.Account;
import com.anz.banking.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.function.Function;

@RestController
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping(value = "/accounts")
    public ResponseEntity<Iterable<Account>> getAccounts() {
        Iterable<Account> result = accountRepository.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/account/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
        Optional<Account> result = accountRepository.findById(id);
        return result.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/account")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Optional<Account> existing = accountRepository.findOne(Example.of(account));
        Account result = existing.map(Function.identity()).orElse(accountRepository.save(account));
        return ResponseEntity.created(URI.create("account/" + result.getAccountNumber())).body(result);
    }

    @PutMapping(value = "/account")
    public ResponseEntity<Account> updateAccount(@RequestBody Account account) {
        Account result = accountRepository.save(account);
        return ResponseEntity.created(URI.create("account/" + result.getAccountNumber())).body(result);
    }
}

