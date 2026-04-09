package com.bank.smartbank.controller;

import com.bank.smartbank.entity.Account;
import com.bank.smartbank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AccountRepository repo;

    // ✅ LOGIN ONLY
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> user) {

        String accountNumber = user.get("accountNumber");
        String password = user.get("password");

        Map<String, Object> response = new HashMap<>();

        Account acc = repo.findByAccountNumber(accountNumber);

        if (acc == null) {
            response.put("status", "fail");
            response.put("message", "Account not found");
            return response;
        }

        if (!acc.getPassword().equals(password)) {
            response.put("status", "fail");
            response.put("message", "Wrong password");
            return response;
        }

        response.put("status", "success");
        response.put("accountNumber", acc.getAccountNumber());
        response.put("balance", acc.getBalance());
        response.put("name", acc.getName());

        return response;
    }
}