package com.enigma.nutrismartbe.controller;

import com.enigma.nutrismartbe.entity.Account;
import com.enigma.nutrismartbe.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/account")
public class AccountController {

    @Autowired
    AccountServiceImpl accountService;

    @GetMapping("/account/profile/{id}")
    public Account getAccount(@PathVariable String id) {
        return accountService.getAccount(id);
    }

    @PutMapping("/account/profile")
    public Account saveProfileAccount(@RequestBody Account account){
        return accountService.saveProfileAccount(account);
    }

    @PostMapping("/sign-in")
    public Account signIn(@RequestBody Account account){
        return accountService.signIn(account);
    }

    @PostMapping("/sign-up")
    public Account saveAccount(@RequestBody Account account) {
        return accountService.saveAccount(account);
    }

    @PostMapping("/admin/sign-in")
    public Account signInAdmin(@RequestBody Account account){
        return accountService.signInAdmin(account);
    }

    @PostMapping("/admin/sign-up")
    public Account saveAdminAccount(@RequestBody Account account){
        return accountService.saveAdminAccount(account);
    }

    @PutMapping("/admin/account/{id}")
    public void deleteAccount(@PathVariable String id){
        accountService.deleteAccount(id);
    }

    @GetMapping("/admin/account")
    public Page<Account> getAllAccount(@RequestParam(name = "page", defaultValue = "1") Integer page,@RequestParam(name = "size", defaultValue = "10") Integer size){
        Pageable pageable = PageRequest.of(page-1, size);
        return accountService.getAllAccounts(pageable);
    }

    @PutMapping("/account/reset-password")
    public Account updatePassword(@RequestBody Account account){
        return accountService.saveAccount(account);
    }
}
