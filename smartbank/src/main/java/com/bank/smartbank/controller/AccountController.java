package com.bank.smartbank.controller;

import com.bank.smartbank.entity.Account;
import com.bank.smartbank.entity.Transaction;
import com.bank.smartbank.repository.TransactionRepository;
import com.bank.smartbank.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
@CrossOrigin(origins = "*")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionRepository transactionRepository;

    // ✅ GET ACCOUNT (balance)
    @GetMapping("/{accountNumber}")
    public Account getAccount(@PathVariable String accountNumber) {
        return accountService.getAccount(accountNumber);
    }

    // ✅ CREATE ACCOUNT
    @PostMapping("/create")
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    // ✅ DEPOSIT
    @PostMapping("/deposit")
    public Account deposit(
            @RequestParam String accountNumber,
            @RequestParam double amount) {

        return accountService.deposit(accountNumber, amount);
    }

    // ✅ WITHDRAW
    @PostMapping("/withdraw")
    public Account withdraw(
            @RequestParam String accountNumber,
            @RequestParam double amount) {

        return accountService.withdraw(accountNumber, amount);
    }

    // ✅ TRANSFER
    @PostMapping("/transfer")
    public Account transfer(
            @RequestParam String fromAccount,
            @RequestParam String toAccount,
            @RequestParam double amount) {

        return accountService.transfer(fromAccount, toAccount, amount);
    }

    // ✅ TRANSACTION HISTORY
    @GetMapping("/transactions/{accountNumber}")
    public List<Transaction> getTransactions(@PathVariable String accountNumber) {
        return transactionRepository.findByAccountNumber(accountNumber);
    }
}