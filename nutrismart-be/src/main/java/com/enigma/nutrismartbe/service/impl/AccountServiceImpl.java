package com.enigma.nutrismartbe.service.impl;

import com.enigma.nutrismartbe.entity.*;
import com.enigma.nutrismartbe.enums.ActiveStatusEnum;
import com.enigma.nutrismartbe.enums.GenderEnum;
import com.enigma.nutrismartbe.enums.StatusUserEnum;
import com.enigma.nutrismartbe.enums.TransactionStatusEnum;
import com.enigma.nutrismartbe.exception.ProhibitedAreaException;
import com.enigma.nutrismartbe.exception.ResourceNotFoundException;
import com.enigma.nutrismartbe.repository.AccountRepository;
import com.enigma.nutrismartbe.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ProfileServiceImpl profileService;

    @Autowired
    StoreServiceImpl storeService;

    @Override
    public Account saveAccount(Account account) {
        account.setIsActive(ActiveStatusEnum.ACTIVE);
        Account newAccount = accountRepository.save(account);
        Profile profile = new Profile();
        profile.setAccount(newAccount);
        profile.setFirstName("Anonymous");
        profile.setLastName("Anonymous");
        profile.setStatus(StatusUserEnum.USER);
        Profile newProfile = profileService.saveProfile(profile);
        newAccount.setProfile(newProfile);
        return accountRepository.save(newAccount);
    }

    @Override
    public Account saveAdminAccount(Account account) {
        account.setIsActive(ActiveStatusEnum.ACTIVE);
        Account newAccount = accountRepository.save(account);
        Profile profile = new Profile();
        profile.setAccount(newAccount);
        profile.setFirstName("Anonymous");
        profile.setLastName("Anonymous");
        profile.setStatus(StatusUserEnum.ADMIN);
        Profile newProfile = profileService.saveProfile(profile);
        newAccount.setProfile(newProfile);
        return accountRepository.save(newAccount);
    }

    @Override
    public Account signInAdmin(Account account) {
        Account admin = accountRepository.findByUserNameAndPassword(account.getUserName(), account.getPassword());
        if(admin.getProfile().getStatus()!=StatusUserEnum.ADMIN)
            throw new ProhibitedAreaException();
        return admin;
    }

    @Override
    public Account signIn(Account account) {
        Account search = accountRepository
                .findAccountsByUserNameEqualsAndPasswordEquals(account
                        .getUserName(), account.getPassword());
        if(search.getProfile().getStatus()!=StatusUserEnum.USER)
            throw new ProhibitedAreaException();
        return search;
    }

    @Override
    public Account getAccount(String id) {
      Account account;
        if (accountRepository.findById(id).isPresent()) {
            account = accountRepository.findById(id).get();
        } else throw new ResourceNotFoundException(id, Account.class);

        return account;
    }

    @Override
    public Account updateAccountPassword(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account deleteAccount(String id) {
        Account target = getAccount(id);
        target.setIsActive(ActiveStatusEnum.INACTIVE);
        return accountRepository.save(target);
    }

    @Override
    public Page<Account> getAllAccounts(Pageable pageable) {
       Page<Account> accounts = accountRepository.findAll(pageable);
        return accounts;
    }

    @Override
    public void removeAccount(String id) {
        accountRepository.deleteById(id);
    }

    @Override
    public Account saveProfileAccount(Account account) {
        account.setEmail(account.getEmail());
        account.setIsActive(account.getIsActive());
        account.setUserName(account.getUserName());
        account.setProfile(account.getProfile());
        return accountRepository.save(account);
    }
}
