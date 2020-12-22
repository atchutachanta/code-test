package com.anz.banking.repository;

import com.anz.banking.entity.Account;
import com.anz.banking.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByAccountAccountNumber(Long id);
}
