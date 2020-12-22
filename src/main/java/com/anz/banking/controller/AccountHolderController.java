package com.anz.banking.controller;

import com.anz.banking.entity.AccountHolder;
import com.anz.banking.repository.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;
import java.util.function.Function;

@RestController
public class AccountHolderController {

    @Autowired
    private AccountHolderRepository accountHolderRepository;


    @GetMapping(value = "/accountHolders")
    public ResponseEntity<Iterable<AccountHolder>> getAccountHolders() {
        Iterable<AccountHolder> result = accountHolderRepository.findAll();
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/accountHolder/{id}")
    public ResponseEntity<AccountHolder> getAccountHolder(@PathVariable Long id) {
        Optional<AccountHolder> result = accountHolderRepository.findById(id);
        return result.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/accountHolder")
    public ResponseEntity<AccountHolder> createAccountHolder(@RequestBody AccountHolder accountHolder) {
        Optional<AccountHolder> existing = accountHolderRepository.findOne(Example.of(accountHolder));
        AccountHolder result = existing.map(Function.identity()).orElse(accountHolderRepository.save(accountHolder));
        return ResponseEntity.created(URI.create("accountHolder/" + result.getId())).body(result);
    }

    @PutMapping(value = "/accountHolder")
    public ResponseEntity<AccountHolder> updateAccountHolder(@RequestBody AccountHolder accountHolder) {
        AccountHolder result = accountHolderRepository.save(accountHolder);
        return ResponseEntity.created(URI.create("accountHolder/" + result.getId())).body(result);
    }
}

