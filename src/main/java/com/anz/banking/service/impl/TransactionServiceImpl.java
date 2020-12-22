package com.anz.banking.service.impl;

import com.anz.banking.entity.Account;
import com.anz.banking.entity.Transaction;
import com.anz.banking.exception.TransactionException;
import com.anz.banking.repository.AccountRepository;
import com.anz.banking.repository.TransactionRepository;
import com.anz.banking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public Transaction AddTransaction(Transaction transaction) {

        if ((transaction.getTransactionType() == Transaction.TransactionType.CREDIT && (transaction.getCreditAmount() == null ||
                transaction.getCreditAmount().compareTo(BigDecimal.ZERO) <= 0)) ||
                (transaction.getTransactionType() == Transaction.TransactionType.DEBIT && (transaction.getDebitAmount() == null ||
                        transaction.getDebitAmount().compareTo(BigDecimal.ZERO) <= 0)))
            throw new TransactionException("Invalid Transaction Amount");

        if (transaction.getAccount() == null)
            throw new TransactionException("Invalid Account Number");

        Optional<Account> account = accountRepository.findById(transaction.getAccount().getAccountNumber());

        if (account.isPresent()) {
            if (transaction.getTransactionType() == Transaction.TransactionType.CREDIT)
                account.get().setBalance(account.get().getBalance().add(transaction.getCreditAmount()));
            else {
                if (account.get().getBalance().compareTo(transaction.getDebitAmount()) >= 0)
                    account.get().setBalance(account.get().getBalance().subtract(transaction.getDebitAmount()));
                else
                    throw new TransactionException("Insufficient funds");
            }
        } else {
            throw new TransactionException("Invalid Account");
        }

        Account result = accountRepository.save(account.get());

        return transactionRepository.save(transaction);

    }

    @Transactional
    public Transaction DeleteTransaction(Long id) {


        Optional<Transaction> result = transactionRepository.findById(id);

        if(result.isPresent()) {
            final Transaction transaction = result.get();
            Optional<Account> account = accountRepository.findById(transaction.getAccount().getAccountNumber());
            if (account.isPresent()) {
                if (transaction.getTransactionType() == Transaction.TransactionType.DEBIT)
                    account.get().setBalance(account.get().getBalance().add(transaction.getDebitAmount()));
                else {
                    if (account.get().getBalance().compareTo(transaction.getCreditAmount()) >= 0)
                        account.get().setBalance(account.get().getBalance().subtract(transaction.getCreditAmount()));
                    else
                        throw new TransactionException("Insufficient funds");
                }
            } else {
                throw new TransactionException("Invalid Account");
            }
            accountRepository.save(account.get());
            transactionRepository.delete(transaction);

        } else {
            throw new TransactionException("Invalid Transaction");
        }
        return result.get();
    }
}
