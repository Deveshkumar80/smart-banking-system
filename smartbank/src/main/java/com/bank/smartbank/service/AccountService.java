package com.bank.smartbank.service;

import com.bank.smartbank.entity.Account;
import com.bank.smartbank.entity.Transaction;
import com.bank.smartbank.repository.AccountRepository;
import com.bank.smartbank.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // ✅ GET ACCOUNT
    public Account getAccount(String accountNumber) {
        Account acc = accountRepository.findByAccountNumber(accountNumber);
        if (acc == null) throw new RuntimeException("Account not found");
        return acc;
    }

    // ✅ CREATE ACCOUNT
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    // ✅ DEPOSIT
    public Account deposit(String accountNumber, double amount) {
        Account acc = accountRepository.findByAccountNumber(accountNumber);
        if (acc == null) throw new RuntimeException("Account not found");

        acc.setBalance(acc.getBalance() + amount);
        Account updated = accountRepository.save(acc);

        transactionRepository.save(
                new Transaction(accountNumber, "DEPOSIT", amount)
        );

        return updated;
    }

    // ✅ WITHDRAW
    public Account withdraw(String accountNumber, double amount) {
        Account acc = accountRepository.findByAccountNumber(accountNumber);
        if (acc == null) throw new RuntimeException("Account not found");

        if (acc.getBalance() < amount)
            throw new RuntimeException("Insufficient balance");

        acc.setBalance(acc.getBalance() - amount);
        Account updated = accountRepository.save(acc);

        transactionRepository.save(
                new Transaction(accountNumber, "WITHDRAW", amount)
        );

        return updated;
    }

    // ✅ TRANSFER
    @Transactional
    public Account transfer(String fromAccount, String toAccount, double amount) {

        Account sender = accountRepository.findByAccountNumber(fromAccount);
        Account receiver = accountRepository.findByAccountNumber(toAccount);

        if (sender == null || receiver == null)
            throw new RuntimeException("Account not found");

        if (sender.getBalance() < amount)
            throw new RuntimeException("Insufficient balance");

        sender.setBalance(sender.getBalance() - amount);
        receiver.setBalance(receiver.getBalance() + amount);

        accountRepository.save(sender);
        accountRepository.save(receiver);

        transactionRepository.save(
                new Transaction(fromAccount, "TRANSFER_OUT", amount)
        );

        transactionRepository.save(
                new Transaction(toAccount, "TRANSFER_IN", amount)
        );

        return sender;
    }
}