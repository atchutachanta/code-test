package com.anz.banking.service;

import com.anz.banking.entity.Transaction;
import com.anz.banking.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public interface TransactionService {

    @Transactional
    Transaction AddTransaction(Transaction transaction);

    Transaction DeleteTransaction(Long id);
}
