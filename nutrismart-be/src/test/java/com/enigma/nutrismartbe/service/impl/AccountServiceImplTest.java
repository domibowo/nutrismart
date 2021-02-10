package com.enigma.nutrismartbe.service.impl;

import com.enigma.nutrismartbe.entity.Account;
import com.enigma.nutrismartbe.entity.Profile;
import com.enigma.nutrismartbe.enums.ActiveStatusEnum;
import com.enigma.nutrismartbe.enums.StatusUserEnum;
import com.enigma.nutrismartbe.exception.ProhibitedAreaException;
import com.enigma.nutrismartbe.exception.ResourceNotFoundException;
import com.enigma.nutrismartbe.repository.AccountRepository;
import com.enigma.nutrismartbe.repository.ProfileRepository;
import com.enigma.nutrismartbe.repository.StoreRepository;
import com.enigma.nutrismartbe.service.AccountService;
import com.enigma.nutrismartbe.service.ProfileService;
import com.enigma.nutrismartbe.service.StoreService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AccountServiceImplTest {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    AccountService accountService;

    @BeforeEach
    public void cleanUp(){
        accountRepository.deleteAll();
    }

//    @AfterEach
//    void cleanUpAgain(){
//        accountRepository.deleteAll();
//    }

    @Test
    void saveAccount_shouldCreateOneAccount_withOneProfile() {
        Account account = new Account();
        account.setIsActive(ActiveStatusEnum.ACTIVE);
        account.setUserName("abc");
        account.setEmail("bbb");
        account.setPassword("ccc");
        Profile profile = new Profile();
        account.setProfile(profile);
        accountService.saveAccount(account);
        assertTrue(accountRepository.findAll().size()==1);
    }

    @Test
    void saveAccount_shouldCreateAUserProfile(){
        Account account = new Account();
        account.setIsActive(ActiveStatusEnum.ACTIVE);
        account.setUserName("abc");
        account.setEmail("bbb");
        account.setPassword("ccc");
        Profile profile = new Profile();
        profile.setStatus(StatusUserEnum.USER);
        account.setProfile(profile);
        accountService.saveAccount(account);
        assertTrue(account.getProfile().getStatus()==StatusUserEnum.USER);
    }

    @Test
    void saveAdminAccount_shouldCreateOneAccount() {
        Account account = new Account();
        account.setIsActive(ActiveStatusEnum.ACTIVE);
        account.setUserName("abc");
        account.setEmail("bbb");
        account.setPassword("ccc");
        Profile profile = new Profile();
        account.setProfile(profile);
        accountService.saveAdminAccount(account);
        assertTrue(accountRepository.findAll().size()==1);
    }

    @Test
    void saveAdminAccount_shouldCreateAProfileAdmin(){
        Account account = new Account();
        account.setIsActive(ActiveStatusEnum.ACTIVE);
        account.setUserName("abc");
        account.setEmail("bbb");
        account.setPassword("ccc");
        Profile profile = new Profile();
        profile.setStatus(StatusUserEnum.ADMIN);
        account.setProfile(profile);
        accountService.saveAdminAccount(account);
        assertTrue(account.getProfile().getStatus()==StatusUserEnum.ADMIN);
    }

    @Test
    void signInAdmin_shouldNotBeNull_WhenUsernameAndPasswordIsCorrect() {
        Account account = new Account();
        account.setIsActive(ActiveStatusEnum.ACTIVE);
        account.setUserName("abc");
        account.setEmail("bbb");
        account.setPassword("ccc");
        Profile profile = new Profile();
        account.setProfile(profile);
        accountService.saveAdminAccount(account);
        Account admin = accountService.signInAdmin(account);
        assertNotNull(admin);
    }

    @Test
    void signInAdmin_shouldThrowExceptionIfStatusNotAnAdmin(){
        Account account = new Account();
        account.setIsActive(ActiveStatusEnum.ACTIVE);
        account.setUserName("abc");
        account.setEmail("bbb");
        account.setPassword("ccc");
        Profile profile = new Profile();
        profile.setStatus(StatusUserEnum.USER);
        account.setProfile(profile);
        accountRepository.save(account);
        assertThrows(ProhibitedAreaException.class, ()->{
            accountService.signInAdmin(account);
        });
    }

    @Test
    void signIn_shouldNotBeNull_WhenUsernameAndPasswordIsCorrect() {
        Account account = new Account();
        account.setIsActive(ActiveStatusEnum.ACTIVE);
        account.setUserName("abc");
        account.setEmail("bbb");
        account.setPassword("ccc");
        Profile profile = new Profile();
        profile.setStatus(StatusUserEnum.USER);
        account.setProfile(profile);
        accountRepository.save(account);
        Account admin = accountService.signIn(account);
        assertNotNull(admin);
    }

    @Test
    void signIn_shouldThrowExceptionIfStatusIsNotAUser(){
        Account account = new Account();
        account.setIsActive(ActiveStatusEnum.ACTIVE);
        account.setUserName("abc");
        account.setEmail("bbb");
        account.setPassword("ccc");
        Profile profile = new Profile();
        profile.setStatus(StatusUserEnum.ADMIN);
        account.setProfile(profile);
        accountRepository.save(account);
        assertThrows(ProhibitedAreaException.class, ()->{
            accountService.signIn(account);
        });
    }

    @Test
    void getAccount_shouldGetTheRightAccountByID() {
        Account account = new Account();
        account.setIsActive(ActiveStatusEnum.ACTIVE);
        account.setUserName("abc");
        account.setEmail("bbb");
        account.setPassword("ccc");
        Profile profile = new Profile();
        account.setProfile(profile);
        accountRepository.save(account);
        Account account1 = accountService.getAccount(account.getId());
        assertTrue(account1.getId()==account.getId());
    }

    @Test
    void getAccount_shouldThrowException_ifAccountIDIsNull(){
        Account account = new Account();
        account.setIsActive(ActiveStatusEnum.ACTIVE);
        account.setUserName("abc");
        account.setEmail("bbb");
        account.setPassword("ccc");
        Profile profile = new Profile();
        account.setProfile(profile);
        accountRepository.save(account);
        assertThrows(ResourceNotFoundException.class, () -> {
            accountService.getAccount("cow");
        });
    }

    @Test
    void updateAccountPassword_shouldUpdateAccountCurrentPassword() {
        Account account = new Account();
        account.setIsActive(ActiveStatusEnum.ACTIVE);
        account.setUserName("abc");
        account.setEmail("bbb");
        account.setPassword("ccc");
        Profile profile = new Profile();
        account.setProfile(profile);
        accountRepository.save(account);

        Account account1 = accountRepository.findById(account.getId()).get();
        account1.setPassword("xyz");
        accountService.updateAccountPassword(account1);

        assertNotEquals(account, account1);
    }

    @Test
    void deleteAccount_shouldChangeStatusAccountToInactive() {
        Account account = new Account();
        account.setIsActive(ActiveStatusEnum.ACTIVE);
        account.setUserName("abc");
        account.setEmail("bbb");
        account.setPassword("ccc");
        Profile profile = new Profile();
        account.setProfile(profile);
        accountRepository.save(account);
        Account account1 = accountService.deleteAccount(account.getId());
        assertTrue(account1.getIsActive()==ActiveStatusEnum.INACTIVE);
    }

    @Test
    void getAllAccounts_shouldGetTwoAccounts_whenInvoked() {
        Account account = new Account();
        account.setIsActive(ActiveStatusEnum.ACTIVE);
        account.setUserName("abc");
        account.setEmail("bbb");
        account.setPassword("ccc");
        Profile profile = new Profile();
        account.setProfile(profile);
        accountRepository.save(account);

        Account account1 = new Account();
        account1.setIsActive(ActiveStatusEnum.ACTIVE);
        account1.setUserName("bcd");
        account1.setEmail("ccc");
        account1.setPassword("ddd");
        Profile profile1 = new Profile();
        account1.setProfile(profile1);
        accountRepository.save(account1);

        Page<Account> accounts = accountService.getAllAccounts(PageRequest.of(0,2));
        assertEquals(2, accounts.getTotalElements());
    }

    @Test
    void removeAccount_shouldDeleteATargetAccount() {
        Account account = new Account();
        account.setIsActive(ActiveStatusEnum.ACTIVE);
        account.setUserName("abc");
        account.setEmail("bbb");
        account.setPassword("ccc");
        Profile profile = new Profile();
        account.setProfile(profile);
        account = accountRepository.save(account);

        accountService.removeAccount(account.getId());
        assertFalse(accountRepository.findById(account.getId()).isPresent());
    }

    @Test
    void saveProfileAccount_shouldChangeProfile() {
        Account account = new Account();
        account.setIsActive(ActiveStatusEnum.ACTIVE);
        account.setUserName("abc");
        account.setEmail("bbb");
        account.setPassword("ccc");
        Profile profile = new Profile();
        account.setProfile(profile);
        accountRepository.save(account);

        Account account1 = accountRepository.findById(account.getId()).get();
        Profile profile1 = account1.getProfile();
        profile1.setStatus(StatusUserEnum.ADMIN);
        accountService.saveProfileAccount(account1);
        assertTrue(account1 != account);
    }
}