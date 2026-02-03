package com.poly.lab8.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.poly.lab8.entity.Account;
import com.poly.lab8.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository repo;

    @Override
    public Account findById(String username) {
        return repo.findById(username).orElse(null);
    }
}
