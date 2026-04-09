package com.bank.smartbank.controller;

import com.bank.smartbank.entity.Transaction;
import com.bank.smartbank.repository.TransactionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionRepository transactionRepository;

    public TransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // GET TRANSACTION HISTORY
    @GetMapping("/{accountNumber}")
    public List<Transaction> getTransactions(@PathVariable String accountNumber) {

        return transactionRepository.findByAccountNumber(accountNumber);
    }
}