package com.enigma.nutrismartbe.service;

import com.enigma.nutrismartbe.entity.Account;
import com.enigma.nutrismartbe.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountService {

    public Account saveAccount(Account account);
    public Account saveAdminAccount(Account account);
    public Account signInAdmin(Account account);
    public Account signIn(Account account);
    public Account getAccount(String id);
    public Account updateAccountPassword(Account account);
    public Account deleteAccount(String id);
    public Page<Account> getAllAccounts(Pageable pageable);
    public void removeAccount(String id);
    public Account saveProfileAccount(Account account);

}